package id.web.devin.mvckolam.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import id.web.devin.mvckolam.model.Cart
import id.web.devin.mvckolam.model.CartDetailModel
import id.web.devin.mvckolam.model.ProdukCart
import id.web.devin.mvckolam.util.CartDetailView
import org.json.JSONArray
import org.json.JSONObject

class CartDetailController(private val context: Context, private val view: CartDetailView) {
    private val cartDetailModel = CartDetailModel(context)

    fun fetchCartDetail(email:String, idKolam: String){
       cartDetailModel.fetchCartDetail(email,idKolam,object : CartDetailModel.CartDetailCallback{
           override fun onCartDetailLoaded(cartDetailList: List<Cart>) {
               view.showCart(cartDetailList)
           }

           override fun onError(errorMessage: String) {
               view.showError(errorMessage)
           }

       })
    }
}