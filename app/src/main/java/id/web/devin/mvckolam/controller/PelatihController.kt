package id.web.devin.mvckolam.controller

import android.content.Context
import id.web.devin.mvckolam.model.Pelatih
import id.web.devin.mvckolam.model.PelatihModel
import id.web.devin.mvckolam.util.PelatihView

class PelatihController(val context: Context, private val view: PelatihView)  {
    private val pelatihModel = PelatihModel(context)

    fun fetchPelatih(idpelatih:String){
       pelatihModel.fetchPelatih(idpelatih,object :PelatihModel.PelatihCallback{
           override fun onPelatihLoaded(pelatihList: List<Pelatih>) {
               view.showPelatih(pelatihList)
           }
           override fun onError(errorMessage: String) {
               view.showError(errorMessage)
           }
           override fun onSuccess() {}
       })
    }

    fun insertPelatih(
        nama: String,
        tglLahir: String,
        kontak: String,
        tglKarir: String,
        gambar:String,
        deskripsi:String,
        idkolam:String
    ){
       pelatihModel.insertPelatih(nama,tglLahir,kontak,tglKarir,gambar,deskripsi,idkolam,object :PelatihModel.PelatihCallback{
           override fun onPelatihLoaded(pelatihList: List<Pelatih>) {}
           override fun onError(errorMessage: String) {
               view.showError(errorMessage)
           }
           override fun onSuccess() {
               view.succes()
           }
       })
    }

    fun updatePelatih(
        nama: String,
        tglLahir: String,
        kontak: String,
        tglKarir: String,
        deskripsi:String,
        idkolam:String
    ){
        pelatihModel.updatePelatih(nama,tglLahir,kontak,tglKarir,deskripsi,idkolam,object : PelatihModel.PelatihCallback{
            override fun onPelatihLoaded(pelatihList: List<Pelatih>) {}
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {
                view.succes()
            }
        })
    }

    fun removePelatih(idpelatih:String){
        pelatihModel.removePelatih(idpelatih,object :PelatihModel.PelatihCallback{
            override fun onPelatihLoaded(pelatihList: List<Pelatih>) {}
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {
                view.succes()
            }
        })
    }
}