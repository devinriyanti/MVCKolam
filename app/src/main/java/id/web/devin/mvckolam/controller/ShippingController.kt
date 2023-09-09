package id.web.devin.mvckolam.controller

import id.web.devin.mvckolam.model.*
import id.web.devin.mvckolam.util.ShippingView

class ShippingController (private val view: ShippingView){

    private val shippingModel = ShippingModel()
    fun fetchShippingCosts(origin: String, destination: String, weight: Int){
        shippingModel.fetchShippingCosts(origin,destination,weight,object :ShippingModel.ShippingCallback{
            override fun onShippingLoaded(shippingList: ShippingResponse) {
                view.showShipping(shippingList)
            }
            override fun onError(errorMessage: String) {
                view.showError(errorMessage)
            }
        })
    }
}