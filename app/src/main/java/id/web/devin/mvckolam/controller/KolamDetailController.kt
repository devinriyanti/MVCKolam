package id.web.devin.mvckolam.controller

import android.content.Context
import id.web.devin.mvckolam.model.Kolam
import id.web.devin.mvckolam.model.KolamDetailModel
import id.web.devin.mvckolam.util.KolamDetailView

class KolamDetailController(private val context: Context, private val view: KolamDetailView) {
    private val kolamDetailModel = KolamDetailModel(context)
    fun fetchDetailKolam(idKolam:String){
        kolamDetailModel.fetchDetailKolam(idKolam,object :KolamDetailModel.KolamDetailCallback{
            override fun onKolamDetailLoaded(kolamDetailList: List<Kolam>) {
                view.showKolam(kolamDetailList)
            }

            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }

        })
    }
}