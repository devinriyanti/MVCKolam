package id.web.devin.mvckolam.controller

import android.content.Context
import id.web.devin.mvckolam.model.*
import id.web.devin.mvckolam.util.TransaksiView

class TransaksiDetailController(private val context: Context, private val view: TransaksiView) {
    private val transaksiModel = TransaksiDetailModel(context)

    fun fetchTransaksiDetail(email:String, status:String, idtransaksi: String) {
        transaksiModel.fetchTransaksiDetail(email,status,idtransaksi,object :TransaksiDetailModel.TransaksiDetailCallback{
            override fun onTransaksiDetailLoaded(transaksiDetailList: List<Transaction>) {
                view.showTransaksi(transaksiDetailList)
            }
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {}
        })
    }

    fun fetchTransaksiAdminDetail(email:String, status:String, idtransaksi: String) {
        transaksiModel.fetchTransaksiAdminDetail(email,status,idtransaksi,object :TransaksiDetailModel.TransaksiDetailCallback{
            override fun onTransaksiDetailLoaded(transaksiDetailList: List<Transaction>) {
                view.showTransaksi(transaksiDetailList)
            }
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {}
        })
    }
}