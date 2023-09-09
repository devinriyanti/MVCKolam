package id.web.devin.mvckolam.model

import id.web.devin.mvckolam.util.RajaOngkirService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShippingModel {
    interface ShippingCallback {
        fun onShippingLoaded(shippingList: ShippingResponse)
        fun onError(errorMessage: String)
    }

    private val rajaOngkirService = Retrofit.Builder()
        .baseUrl("https://api.rajaongkir.com/starter/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RajaOngkirService::class.java)

    fun fetchShippingCosts(origin: String, destination: String, weight: Int,callback: ShippingCallback){
//         val apiKey = "f6c65ee25a36a2f2a7767cc130d4e9a6" //devinriyantii
        val apiKey = "0178ae2a9f1df06a92c967cdd512cede" //vinariyantii
        val request = ShippingCostRequest(origin, destination, weight, apiKey, "jne")
        val response = rajaOngkirService.calculateShippingCosts(request)
        response.enqueue(object : Callback<ShippingResponse> {
            override fun onResponse(call: Call<ShippingResponse>, response: Response<ShippingResponse>) {
                if (response.isSuccessful) {
                    val shippingCostResponse = response.body()
                    callback.onShippingLoaded(shippingCostResponse!!)
                } else {
                    callback.onError("Failed to get shipping cost")
                }
            }
            override fun onFailure(call: Call<ShippingResponse>, t: Throwable) {
                callback.onError("Network error: " + t.message)
            }
        })
    }
}