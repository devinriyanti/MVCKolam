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

class CartDetailModel(private val context: Context) {
    interface CartDetailCallback {
        fun onCartDetailLoaded(cartDetailList: List<Cart>)
        fun onError(errorMessage: String)
    }

    private val TAG = "volleyTAG"
    private var queue: RequestQueue?= null

    fun fetchCartDetail(email:String, idKolam: String, callback: CartDetailCallback){
        queue = Volley.newRequestQueue(context)
        val url = "https://lokowai.shop/cartdetail.php"
        val stringReq = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                val cart = parseCartDetail(response,callback)
                Log.d("successCart", cart.toString())
                callback.onCartDetailLoaded(cart)
            },
            Response.ErrorListener { error ->
                Toast.makeText(context,"Kesalahan Saat Menampilkan Produk", Toast.LENGTH_SHORT).show()
                Log.d("cartError",error.toString())
                callback.onError(error.toString())
            }){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["email"] = email
                params["idkolam"] = idKolam
                return params
            }
        }
        stringReq.tag = TAG
        queue?.add(stringReq)
    }

    private fun parseCartDetail(response: String?,callback: CartDetailCallback): List<Cart> {
        val carts = mutableListOf<Cart>()
        try {
            val cartJSON = JSONObject(response)
            val id = cartJSON.getString("IdKolam")
            val nama = cartJSON.getString("namaKolam")
            val idkota = cartJSON.getString("idkota")
            val produkArray = cartJSON.getJSONArray("produk")
            val produkList = parseProductCart(produkArray)

            val cart = Cart(id, nama, idkota,produkList as ArrayList<ProdukCart>)
            carts.add(cart)
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