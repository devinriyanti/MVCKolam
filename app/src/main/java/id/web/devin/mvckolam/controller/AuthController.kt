package id.web.devin.mvckolam.controller

import android.content.Context
import com.android.volley.RequestQueue
import id.web.devin.mvckolam.model.AuthModel
import id.web.devin.mvckolam.util.AuthView

class AuthController(private val context: Context, private val view: AuthView) {

    private val authModel = AuthModel(context)
    fun loginUser(email:String, pwd:String){
        authModel.loginUser(email,pwd,object :AuthModel.AuthCallback{
            override fun onSuccess() {
                view.success()
            }
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
        })
    }

    fun registerUser(
        nama: String,
        email: String,
        alamat:String,
        noTelp: String,
        pwd: String,
        role: String,
        idkota:String
    ){
        authModel.registerUser(nama,email,alamat,noTelp,pwd,role,idkota,object :AuthModel.AuthCallback{
            override fun onSuccess() {
                view.success()
            }
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
        })
    }

    fun registerAdmin(
        nama: String,
        email: String,
        alamat:String,
        norekening:String,
        namarekening:String,
        noTelp: String,
        pwd: String,
        role: String,
        idkota:String
    ){
        authModel.registerAdmin(nama,email,alamat,norekening,namarekening,noTelp,pwd,role,idkota,object :AuthModel.AuthCallback{
            override fun onSuccess() {
                view.success()
            }

            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
        })
    }
}

