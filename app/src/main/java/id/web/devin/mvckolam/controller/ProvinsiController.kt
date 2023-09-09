package id.web.devin.mvckolam.controller

import android.content.Context
import id.web.devin.mvckolam.model.*
import id.web.devin.mvckolam.util.ProvinsiView

class ProvinsiController (private val context: Context, private val view:ProvinsiView) {
    private val provinsiModel = ProvinsiModel(context)

    fun fetchProvinsi() {
        provinsiModel.fetchProvinsi(object : ProvinsiModel.ProvinsiCallback {
            override fun onProvinsiLoaded(provinsiList: List<Provinsi>) {
                view.showProvinsi(provinsiList)
            }
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
        })
    }
}