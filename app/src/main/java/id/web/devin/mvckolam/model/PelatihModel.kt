package id.web.devin.mvckolam.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class PelatihModel(private val context:Context) {
    interface PelatihCallback {
        fun onPelatihLoaded(pelatihList: List<Pelatih>)
        fun onError(errorMessage: String)
        fun onSuccess()
    }

    private val TAG = "volleyTAG"
    private var queue: RequestQueue?= null

    fun fetchPelatih(idpelatih:String,callback: PelatihCallback){
        queue = Volley.newRequestQueue(context)
        var url = "https://lokowai.shop/pelatihdetail.php?id=$idpelatih"
        val stringReq = StringRequest(Request.Method.GET,url,
            {response->
                Log.d("successProduk", response.toString())
                val produk = parsePelatih(response)
                Log.d("success", produk.toString())
                callback.onPelatihLoaded(produk)
            },
            {error->
                Log.d("errorProduk", error.toString())
                callback.onError(error.toString())
            }
        ).apply {
            tag = "TAG"
        }
        queue?.add(stringReq)
    }

    fun insertPelatih(
        nama: String,
        tglLahir: String,
        kontak: String,
        tglKarir: String,
        gambar:String,
        deskripsi:String,
        idkolam:String,
        callback: PelatihCallback
    ){
        queue = Volley.newRequestQueue(context)
        val url = "https://lokowai.shop/insertpelatih.php"
        val stringReq = object : StringRequest(
            Method.POST, url,
            Response.Listener{ response ->
                var data = JSONObject(response)
                var status = data.getString("result")
                if(status.equals("success")){
                    callback.onSuccess()
                    Log.d("showvolley", response.toString())
                }else{
                    callback.onError("Gagal menambah pelatih")
                    Log.d("showError", response.toString())
                }
            },
            Response.ErrorListener { error ->
                // Menangani kesalahan
                Toast.makeText(context,"Kesalahan Saat Mengakses Basis Data", Toast.LENGTH_SHORT).show()
                Log.d("showvolley", error.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["nama"] = nama
                params["tanggal_lahir"] = tglLahir
                params["kontak"] = kontak
                params["mulai_karir"] = tglKarir
                params["gambar"] = gambar
                params["deskripsi"] = deskripsi
                params["idkolam"] = idkolam
                return params
            }
        }
        stringReq.tag = TAG
        queue?.add(stringReq)
    }

    fun updatePelatih(
        nama: String,
        tglLahir: String,
        kontak: String,
        tglKarir: String,
        deskripsi:String,
        idkolam:String,
        callback: PelatihCallback
    ){
        queue = Volley.newRequestQueue(context)
        val url = "https://lokowai.shop/editpelatih.php"
        val stringReq = object  : StringRequest(
            Method.POST, url,
            Response.Listener {
                val data = JSONObject(it)
                Log.d("editpelatih", data.toString())
                val status = data.getString("result")
                if(status.equals("success")){
                    callback.onSuccess()
                    Log.d("showSuccess",it.toString())
                }else{
                    callback.onError("Gagal mengubah data pelatih")
                    Log.d("showError",it.toString())
                }
            },
            Response.ErrorListener {
                Toast.makeText(context,"Kesalahan Saat Mengakses Basis Data", Toast.LENGTH_SHORT).show()
                Log.d("updateError", it.toString())
            }){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["nama"] = nama
                params["tanggal_lahir"] = tglLahir
                params["kontak"] = kontak
                params["mulai_karir"] = tglKarir
                params["deskripsi"] = deskripsi
                params["idpelatih"] = idkolam
                return params
            }
        }
        stringReq.tag = TAG
        queue?.add(stringReq)
    }

    fun removePelatih(idpelatih:String,callback: PelatihCallback){
        queue = Volley.newRequestQueue(context)
        val url = "https://lokowai.shop/removepelatih.php"
        val stringReq = object  : StringRequest(
            Method.POST, url,
            Response.Listener {
                Log.d("tes", it)
                if(it.equals("[]")){
                    callback.onError("kosong")
                }else{
                    val data = JSONObject(it)
                    val status = data.getString("result")
                    if(status.equals("success")){
                        callback.onSuccess()
                        Log.d("showSuccess",it.toString())
                    }else{
                        Log.d("showError",it.toString())
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
                params["idpelatih"] = idpelatih
                return params
            }
        }
        stringReq.tag = TAG
        queue?.add(stringReq)
    }

    private fun parsePelatih(response: String?): List<Pelatih> {
        val pelatihList = mutableListOf<Pelatih>()

        val pelatihJSON = JSONObject(response)
        val id = pelatihJSON.getString("id")
        val nama = pelatihJSON.getString("nama")
        val tglLahir = pelatihJSON.getString("tanggal_lahir")
        val kontak = pelatihJSON.getString("kontak")
        val tglKarir = pelatihJSON.getString("mulai_karir")
        val deskripsi = pelatihJSON.getString("deskripsi")
        val jenisKelamin = pelatihJSON.getString("jenis_kelamin")
        val gambarUrl = pelatihJSON.getString("gambar")

        val pelatih = Pelatih(id, nama, tglLahir, kontak, tglKarir, deskripsi, jenisKelamin, gambarUrl)
        pelatihList.add(pelatih)

        return pelatihList
    }
}