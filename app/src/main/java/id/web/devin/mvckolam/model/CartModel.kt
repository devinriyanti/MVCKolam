package id.web.devin.mvckolam.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class CartModel(private val context: Context) {
    interface CartCallback {
        fun onCartLoaded(cartList: List<Cart>)
        fun onError(errorMessage: String)
        fun onSuccess()
    }

    private val TAG = "volleyTAG"
    private var queue: RequestQueue?= null

    fun addToCart(
        idKolam: String,
        total_harga: Double,
        email: String,
        idproduk: String,
        qty: Int,
        harga: Double,
        diskon: Double,
        callback: CartCallback,
    ){
        queue = Volley.newRequestQueue(context)
        val url = "https://lokowai.shop/addtocart.php"
        val stringReq = object : StringRequest(
            Method.POST,url,
            Response.Listener { response ->
                Log.d("respon", response)
                var data = JSONObject(response)
                var status = data.getString(("result"))
                if(status.equals("success")){
                    callback.onSuccess()
                    Log.d("addtocartVolley",status.toString())
                }else{
                    callback.onError("Gagal Memasukan Ke Keranjang")
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(context,"Kesalahan Saat Menambahkan Produk", Toast.LENGTH_SHORT).show()
                Log.d("showvolley", error.toString())
            }){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["idkolam"] = idKolam
                params["total"] = total_harga.toString()
                params["email"] = email
                params["idproduk"] = idproduk
                params["qty"] = qty.toString()
                params["harga"] = harga.toString()
                params["diskon"] = diskon.toString()
                return params
            }
        }
        stringReq.tag = TAG
        queue?.add(stringReq)
    }

    fun fetchCart(email:String,callback: CartCallback) {
        queue = Volley.newRequestQueue(context)
        val url = "https://lokowai.shop/cartlist.php"
        val stringReq = object : StringRequest(
            Method.POST, url,
            { response ->
                val cart = parseCart(response,callback)
                Log.d("successCart", cart.toString())
                callback.onCartLoaded(cart)
            },
            { error ->
                Log.d("errorCart", error.toString())
                callback.onError(error.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["email"] = email
                return params
            }
        }
        stringReq.tag = TAG
        queue?.add(stringReq)
    }

    fun updateQty(qty: Int, idkeranjang: Int, idproduk: String,callback: CartCallback){
        queue = Volley.newRequestQueue(context)
        val url = "https://lokowai.shop/updatecart.php"
        val stringReq = object  : StringRequest(
            Method.POST, url,
            Response.Listener {
                val data = JSONObject(it)
                Log.d("dataUpdateCart", data.toString())
                val status = data.getString("result")
                if(status.equals("success")){
                    Log.d("showSuccess",it.toString())
                }else{
                    Log.d("showError",it.toString())
                    callback.onError("Gagal mengubah kuantitas")
                }
            },
            Response.ErrorListener {
                Toast.makeText(context,"Kesalahan Saat Mengakses Basis Data", Toast.LENGTH_SHORT).show()
                Log.d("updateError", it.toString())
            }){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["qty"] = qty.toString()
                params["idkeranjang"] = idkeranjang.toString()
                params["idproduk"] = idproduk
                return params
            }
        }
        stringReq.tag = TAG
        queue?.add(stringReq)
    }

    fun removeCart(idkeranjang:Int, idproduk: String, callback: CartCallback){
        queue = Volley.newRequestQueue(context)
        val url = "https://lokowai.shop/removecart.php"
        val stringReq = object  : StringRequest(
            Method.POST, url,
            Response.Listener {
                Log.d("tes", it)
                if(it.equals("[]")){
                    callback.onError("Kosong")
                }else{
                    val data = JSONObject(it)
                    val status = data.getString("result")
                    if(status.equals("success")){
                        callback.onSuccess()
                        Log.d("showSuccess",it.toString())
                    }else{
                        callback.onError(it.toString())
                    }
                }
            },
            Response.ErrorListener {
                Toast.makeText(context,"Kesalahan Saat Mengakses Basis Data", Toast.LENGTH_SHORT).show()
                Log.d("removeError", it.toString())
            }){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"] = idkeranjang.toString()
                params["idproduk"] = idproduk
                return params
            }
        }
        stringReq.tag = TAG
        queue?.add(stringReq)
    }

    private fun parseCart(response: String?, callback: CartCallback): List<Cart> {
        val carts = mutableListOf<Cart>()
        try {
            val jsonArray = JSONArray(response)
            Log.d("jsonTransaksi", jsonArray.toString())

            for (i in 0 until jsonArray.length()) {
                val cartJSON: JSONObject = jsonArray.getJSONObject(i)
                val id = cartJSON.getString("IdKolam")
                val nama = cartJSON.getString("namaKolam")
                val idkota = cartJSON.getString("idkota")
                val produkArray = cartJSON.getJSONArray("produk")
                val produkList = parseProductCart(produkArray)

                val cart = Cart(id, nama, idkota,produkList as ArrayList<ProdukCart>)
                carts.add(cart)
            }
        } catch (e: Exception) {
            // Handle JSON parsing error
            callback.onError("Keranjang Kosong")
        }
        return carts
    }

    private fun parseProductCart(array: JSONArray): Any {
        val produkList = mutableListOf<ProdukCart>()

        for (i in 0 until array.length()) {
            val produkJSON: JSONObject = array.getJSONObject(i)
            val idkeranjangs = produkJSON.getInt("idkeranjangs")
            val idkolam = produkJSON.getString("idkolam")
            val total_harga = produkJSON.getDouble("total_harga")
            val email = produkJSON.getString("email_pengguna")
            val idproduk = produkJSON.getString("idproduk")
            val namaProduk= produkJSON.getString("namaProduk")
            val harga= produkJSON.getDouble("harga")
            val qty= produkJSON.getInt("qty")
            val diskon= produkJSON.getDouble("diskon")
            val gambar= produkJSON.getString("url_gambar")
            val berat= produkJSON.getInt("berat")
            val norekening= produkJSON.getString("norekening")
            val namaRekening= produkJSON.getString("nama_rekening")

            val produk = ProdukCart(idkeranjangs, idkolam, total_harga, email, idproduk, namaProduk, harga, qty, diskon, gambar, berat, norekening,namaRekening)
            produkList.add(produk)
        }

        return produkList
    }
}