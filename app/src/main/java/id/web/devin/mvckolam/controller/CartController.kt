package id.web.devin.mvckolam.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import id.web.devin.mvckolam.model.*
import id.web.devin.mvckolam.util.CartView
import org.json.JSONArray
import org.json.JSONObject

class CartController(private val context: Context, private val view: CartView) {
    private val TAG = "volleyTAG"
    private var queue: RequestQueue?= null

    private val cartModel = CartModel(context)
    fun addToCart(
        idKolam: String,
        total_harga: Double,
        email: String,
        idproduk: String,
        qty: Int,
        harga: Double,
        diskon: Double,
    ){
        cartModel.addToCart(idKolam,total_harga,email,idproduk,qty,harga,diskon,object :CartModel.CartCallback{
            override fun onCartLoaded(cartList: List<Cart>) {
                view.showCart(cartList)
            }
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {}
        })
    }

    fun fetchCart(email:String) {
        cartModel.fetchCart(email, object :CartModel.CartCallback{
            override fun onCartLoaded(cartList: List<Cart>) {
                view.showCart(cartList)
            }

            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
            override fun onSuccess() {}
        })
    }

    fun updateQty(qty: Int, idkeranjang: Int, idproduk: String){
       cartModel.updateQty(qty,idkeranjang,idproduk, object :CartModel.CartCallback{
           override fun onCartLoaded(cartList: List<Cart>) {}
           override fun onError(errorMessage: String) {
               view.showError(errorMessage)
           }
           override fun onSuccess() {
               view.success()
           }
       })
    }

    fun removeCart(idkeranjang:Int, idproduk: String){
        cartModel.removeCart(idkeranjang,idproduk,object : CartModel.CartCallback{
            override fun onCartLoaded(cartList: List<Cart>) {}

            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }

            override fun onSuccess() {
                view.success()
            }

        })
    }
}