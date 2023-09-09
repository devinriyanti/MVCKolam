package id.web.devin.mvckolam.controller

import android.content.Context
import id.web.devin.mvckolam.model.Transaction
import id.web.devin.mvckolam.model.TransaksiModel
import id.web.devin.mvckolam.util.TransaksiView

class TransaksiController(private val context: Context, private val view: TransaksiView) {
    private val transaksiModel = TransaksiModel(context)

    fun insertTransaksi(
        ongkir:Int,
        idkeranjang:Int,
        idkolam:String,
        email_pengguna:String,
        idkota:Int,
        alamat:String
    ){

    }

    fun fetchTransaksi(email:String, status:String) {
        transaksiModel.updateBuktiPembayaran(email,status,object : TransaksiModel.TransaksiCallback{
            override fun onTransaksiLoaded(transaksiList: List<Transaction>) {
                view.showTransaksi(transaksiList)
            }
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {}
        })
    }

    fun fetchTransaksiAdmin(email:String, status:String) {
        transaksiModel.fetchTransaksiAdmin(email,status,object : TransaksiModel.TransaksiCallback{
            override fun onTransaksiLoaded(transaksiList: List<Transaction>) {
                view.showTransaksi(transaksiList)
            }
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {}
        })
    }

    fun updateStatus(idtransaksi: String, status: String){
        transaksiModel.updateStatus(idtransaksi,status,object : TransaksiModel.TransaksiCallback{
            override fun onTransaksiLoaded(transaksiList: List<Transaction>) {}
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {
                view.success()
            }
        })
    }

    fun konfirmasiTransaksi(idtransaksi: String, status: String, no_resi:String){
        transaksiModel.konfirmasiTransaksi(idtransaksi,status,no_resi,object : TransaksiModel.TransaksiCallback{
            override fun onTransaksiLoaded(transaksiList: List<Transaction>) {}
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {
                view.success()
            }
        })
    }

    fun updateBuktiPembayaran(urlBukti:String, idtransaksi: String){
        transaksiModel.updateBuktiPembayaran(urlBukti,idtransaksi,object : TransaksiModel.TransaksiCallback{
            override fun onTransaksiLoaded(transaksiList: List<Transaction>) {}
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {
                view.success()
            }
        })
    }
}


