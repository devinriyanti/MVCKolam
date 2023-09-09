package id.web.devin.mvckolam.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import id.web.devin.mvckolam.model.*
import id.web.devin.mvckolam.util.KolamView
import org.json.JSONArray
import org.json.JSONObject

class KolamController(private val context: Context, private val view: KolamView) {
    private val kolamModel = KolamModel(context)

    fun fetchKolam(cari:String) {
        kolamModel.fetchKolam(cari,object :KolamModel.KolamCallback{
            override fun onKolamLoaded(kolamList: List<Kolam>) {
                view.showKolam(kolamList)
            }

            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }

            override fun onSuccess() {}
        })
    }

    fun fetchKolamAdmin(email:String, role:String){
        kolamModel.fetchKolamAdmin(email,role,object :KolamModel.KolamCallback{
            override fun onKolamLoaded(kolamList: List<Kolam>) {
                view.showKolam(kolamList)
            }

            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }

            override fun onSuccess() {}

        })
    }

    fun insertKolam(
        nama: String,
        alamat: String,
        deskripsi: String,
        gambar: String,
        lokasi: String,
        email:String
    ){
        kolamModel.insertKolam(nama,alamat,deskripsi,gambar,lokasi,email,object :KolamModel.KolamCallback{
            override fun onKolamLoaded(kolamList: List<Kolam>) {}

            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }

            override fun onSuccess() {
                view.success()
            }

        })
    }

    fun updateKolam(
        nama:String,
        alamat:String,
        deskripsi:String,
        lokasi:String,
        idkolam:String
    ){
       kolamModel.updateKolam(nama,alamat,deskripsi,lokasi,idkolam,object :KolamModel.KolamCallback{
           override fun onKolamLoaded(kolamList: List<Kolam>) {}

           override fun onError(errorMessage: String) {
               view.showError(errorMessage)
           }

           override fun onSuccess() {
               view.success()
           }

       })
    }

    fun removeKolam(idkolam:String){
        kolamModel.removeKolam(idkolam, object :KolamModel.KolamCallback{
            override fun onKolamLoaded(kolamList: List<Kolam>) {}

            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }

            override fun onSuccess() {
                view.success()
            }

        })
    }
    fun updateMaintenance(status:Int, idkolam: String){
        kolamModel.updateMaintenance(status,idkolam,object :KolamModel.KolamCallback{
            override fun onKolamLoaded(kolamList: List<Kolam>) {}

            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }

            override fun onSuccess() {
                view.success()
            }

        })
    }
    fun updateStatus(status:Int, idkolam: String){
       kolamModel.updateStatus(status,idkolam,object :KolamModel.KolamCallback{
           override fun onKolamLoaded(kolamList: List<Kolam>) {}

           override fun onError(errorMessage: String) {
               view.showError(errorMessage)
           }

           override fun onSuccess() {
               view.success()
           }
       })
    }
}