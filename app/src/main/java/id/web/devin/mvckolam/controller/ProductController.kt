package id.web.devin.mvckolam.controller

import android.content.Context
import id.web.devin.mvckolam.model.ProductModel
import id.web.devin.mvckolam.model.Produk
import id.web.devin.mvckolam.util.ProductView

class ProductController (val context: Context, private val view: ProductView) {
    private val productModel = ProductModel(context)

    fun fetchProduct(idProduk:String){
        productModel.fetchProduct(idProduk,object :ProductModel.ProductCallback{
            override fun onProductLoaded(productList: List<Produk>) {
                view.showProduk(productList)
            }
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {}
        })
    }

    fun insertProduct(
        nama: String,
        deskripsi: String,
        qty: Int,
        harga: Double,
        diskon: Double,
        gambar:String,
        berat:Int,
        idkolam:String
    ){
        productModel.insertProduct(nama,deskripsi,qty,harga,diskon,gambar,berat,idkolam,object :ProductModel.ProductCallback{
            override fun onProductLoaded(productList: List<Produk>) {}
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {
                view.success()
            }
        })
    }

    fun updateProduk(
        nama: String,
        deskripsi: String,
        qty: Int,
        harga: Double,
        diskon: Double,
        berat:Int,
        idproduk:String
    ){
        productModel.updateProduk(nama,deskripsi,qty,harga,diskon,berat,idproduk,object :ProductModel.ProductCallback{
            override fun onProductLoaded(productList: List<Produk>) {}
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {
                view.success()
            }
        })
    }

    fun updateStatus(status:Int, idproduk: String){
        productModel.updateStatus(status,idproduk,object : ProductModel.ProductCallback{
            override fun onProductLoaded(productList: List<Produk>) {}
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {
                view.success()
            }
        })
    }

    fun removeProduk(idproduk:String){
        productModel.removeProduk(idproduk,object :ProductModel.ProductCallback{
            override fun onProductLoaded(productList: List<Produk>) {}
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {
                view.success()
            }

        })
    }
}


