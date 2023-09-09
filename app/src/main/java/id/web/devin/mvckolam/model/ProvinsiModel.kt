package id.web.devin.mvckolam.model

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class ProvinsiModel(private val context:Context) {
    interface ProvinsiCallback {
        fun onProvinsiLoaded(provinsiList: List<Provinsi>)
        fun onError(errorMessage: String)
    }

    private val TAG = "volleyTAG"
    private var queue: RequestQueue?= null

    fun fetchProvinsi(callback: ProvinsiCallback) {
        queue = Volley.newRequestQueue(context)
        val url = "https://lokowai.shop/kotalist.php"
        val stringReq = StringRequest(
            Request.Method.GET, url,
            {response->
                val provinsi = parseProvinsi(response,callback)
                callback.onProvinsiLoaded(provinsi)

            },
            {error->
                Log.d("successKolam", error.toString())
                callback.onError(error.toString())
            })
        stringReq.tag = TAG
        queue?.add(stringReq)
    }

    private fun parseProvinsi(response: String?, callback: ProvinsiCallback): List<Provinsi> {
        val provinsis = mutableListOf<Provinsi>()
        try {
            val jsonArray = JSONArray(response)
            Log.d("jsonkolam", jsonArray.toString())
            for (i in 0 until jsonArray.length()) {
                val kolamJSON: JSONObject = jsonArray.getJSONObject(i)
                val id = kolamJSON.getString("idprovinsi")
                val nama = kolamJSON.getString("nama")

                val kotaArray = kolamJSON.getJSONArray("kota")
                val kotaList = parseKotaList(kotaArray)

                val provinsi = Provinsi(id.toInt(), nama, kotaList as ArrayList<Kota>)
                provinsis.add(provinsi)
            }
        } catch (e: Exception) {
            // Handle JSON parsing error
            callback.onError("Tidak Ada Daftar Provinsi")
        }
        return provinsis
    }

    private fun parseKotaList(array: JSONArray): List<Kota> {
        val kotaList = mutableListOf<Kota>()

        for (i in 0 until array.length()) {
            val tiketJSON: JSONObject = array.getJSONObject(i)
            val idkota = tiketJSON.getString("idkota")
            val nama = tiketJSON.getString("nama_kota")
            val tipe = tiketJSON.getString("type")
            val kode_pos = tiketJSON.getString("kode_pos")

            val kota = Kota(idkota.toInt(), nama, tipe, kode_pos)
            kotaList.add(kota)
        }

        return kotaList
    }
}