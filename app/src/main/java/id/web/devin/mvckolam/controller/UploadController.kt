package id.web.devin.mvckolam.controller

import id.web.devin.mvckolam.model.UploadModel
import id.web.devin.mvckolam.model.UploadResponse
import id.web.devin.mvckolam.util.UploadView
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadController(private val view:UploadView) {
    private val uploadModel= UploadModel()

    fun uploadImage(imageFile: MultipartBody.Part,folder:RequestBody) {
        uploadModel.uploadImage(imageFile,folder,object :UploadModel.UploadCallback{
            override fun onUploadLoaded(upload: UploadResponse) {
                view.UploadSuccess(upload)
            }
            override fun onError(errorMessage: String) {
                view.uploadError(errorMessage)
            }
        })
    }
}