package id.web.devin.mvckolam.controller

import android.content.Context
import id.web.devin.mvckolam.model.Pengguna
import id.web.devin.mvckolam.model.ProfileModel
import id.web.devin.mvckolam.util.ProfilView

class ProfileController(private val context: Context, private val view: ProfilView) {
    private val profilModel = ProfileModel(context)

    fun fetchProfil(email: String) {
        profilModel.fetchProfil(email, object : ProfileModel.ProfilCallback {
            override fun onProfilLoaded(profilList: List<Pengguna>) {
                view.showProfil(profilList)
            }

            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }

            override fun onSuccess() {}
        })
    }

    fun updateUser(
        email: String,
        nama: String,
        alamat: String,
        noTelp: String,
        gender: String,
        tglLahir: String
    ) {
        profilModel.updateUser(
            email,
            nama,
            alamat,
            noTelp,
            gender,
            tglLahir,
            object : ProfileModel.ProfilCallback {
                override fun onProfilLoaded(profilList: List<Pengguna>) {}

                override fun onError(errorMessage: String) {
                    view.showError(errorMessage)
                }

                override fun onSuccess() {
                    view.success()
                }

            })
    }

    fun updateKatasandi(email: String,passwordNew:String){
        profilModel.updateKatasandi(email,passwordNew,object :ProfileModel.ProfilCallback{
            override fun onProfilLoaded(profilList: List<Pengguna>) {}

            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }

            override fun onSuccess() {
                view.success()
            }

        })
    }
}