package id.web.devin.mvckolam.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ProfileModel(private val context: Context) {
    interface ProfilCallback {
        fun onProfilLoaded(profilList: List<Pengguna>)
        fun onError(errorMessage: String)
        fun onSuccess()
    }

    private val TAG = "volleyTAG"
    private var queue: RequestQueue?= null

    fun fetchProfil(email:String, callback: ProfilCallback){
        queue = Volley.newRequestQueue(context)
        val url = "https://lokowai.shop/profildetail.php"
        val stringReq = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                val profil = parseProfil(response,callback)
                Log.d("succesProfil", profil.toString())
                callback.onProfilLoaded(profil)
            },
            Response.ErrorListener {
                Log.d("errorProfil", it.toString())
                callback.onError(it.toString())
            }){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["email"] = email
                return params
            }
        }
        stringReq.tag = TAG
        queue?.add(stringReq)
    }

    fun updateUser(
        email:String,
        nama: String,
        alamat: String,
        noTelp: String,
        gender:String,
        tglLahir:String,
        callback: ProfilCallback
    ){
        queue = Volley.newRequestQueue(context)
        val url = "https://lokowai.shop/editpengguna.php"
        val stringReq = object : StringRequest(
            Method.POST, url,
            Response.Listener{ response ->
                val data = JSONObject(response)
                Log.d("dataProfil", data.toString())
                val status = data.getString("result")
                if(status.equals("success")){
                    callback.onSuccess()
                    Log.d("showSuccess",response.toString())
                }else{
                    callback.onError("Gagal Mengubah Data Diri")
                    Log.d("showError",response.toString())
                }
            },
            {
                Toast.makeText(context,"Kesalahan Saat Mengakses Basis Data", Toast.LENGTH_SHORT).show()
                Log.d("updateError", it.toString())
            }){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["email"] = email
                params["nama"] = nama
                params["alamat"] = alamat
                params["telepon"] = noTelp
                params["gender"] = gender
                params["tglLahir"] = tglLahir
                return params
            }
        }
        stringReq.tag = TAG
        queue?.add(stringReq)
    }

    fun updateKatasandi(
        email:String,
        password: String,
        callback: ProfilCallback
    ){
        queue = Volley.newRequestQueue(context)
        val url = "https://lokowai.shop/upadatepassword.php"
        val stringReq = object : StringRequest(
            Method.POST, url,
            Response.Listener{ response ->
                val data = JSONObject(response)
                Log.d("update", data.toString())
                val status = data.getString("result")
                if(status.equals("success")){
                    Log.d("showSuccess",response.toString())
                }else{
                    callback.onError("Gagal Mengubah Kata Sandi")
                    Log.d("showError",response.toString())
                }
            },
            {
                Toast.makeText(context,"Kesalahan Saat Mengakses Basis Data", Toast.LENGTH_SHORT).show()
                Log.d("updateError", it.toString())
            }){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["email"] = email
                params["nama"] = password
                return params
            }
        }
        stringReq.tag = TAG
        queue?.add(stringReq)
    }

    private fun parseProfil(response: String, callback: ProfilCallback): List<Pengguna> {
        val profiles = mutableListOf<Pengguna>()
        try {
            val profilJSON = JSONObject(response)
            val email = profilJSON.getString("email")
            val nama = profilJSON.getString("nama")
            val alamat = profilJSON.getString("alamat")
            val telepon:String = profilJSON.getString("telepon")
            val jenis_kelamin = profilJSON.getString("jenis_kelamin")
            val norekening = profilJSON.getString("norekening")
            val namaRekening = profilJSON.getString("nama_rekening")
            val tglLahir = profilJSON.getString("tanggal_lahir")
            val pwd = profilJSON.getString("password")
            val roleStr = profilJSON.getString("role")
            val role = Role.valueOf(roleStr)
            val kota = profilJSON.getString("nama_kota")
            val idkota = profilJSON.getString("idkota")
            val profil = Pengguna(email,nama, alamat, telepon,jenis_kelamin,norekening,namaRekening,tglLahir,pwd,role,kota,idkota)
            profiles.add(profil)
        }catch (e: Exception){
            callback.onError("Tidak Ada Data Pengguna")
        }
        return profiles
    }
}