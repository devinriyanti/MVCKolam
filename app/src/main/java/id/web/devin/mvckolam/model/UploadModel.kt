package id.web.devin.mvckolam.model

import android.util.Log
import id.web.devin.mvckolam.util.UploadService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UploadModel {
    interface UploadCallback {
        fun onUploadLoaded(upload: UploadResponse)
        fun onError(errorMessage: String)
    }

    private val uploadService: UploadService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://lokowai.shop/") // Ganti dengan URL API yang sesuai
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(UploadService::class.java)
    }

    fun uploadImage(imageFile: MultipartBody.Part, folder: RequestBody,callback: UploadCallback) {
        val call: Call<UploadResponse> = uploadService.uploadImage(imageFile,folder)
        call.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(call: Call<UploadResponse>, response: Response<UploadResponse>) {
                Log.d("respon", response.toString())
                if (response.isSuccessful) {
                    val uploadResponse = response.body()
                    Log.d("upload", response.toString())
                    if (uploadResponse != null) {
                        callback.onUploadLoaded(uploadResponse)
                    } else {
                        callback.onError("Anda belum memilih foto")
                    }
                } else {
                    callback.onError("Gagal Mengunggah Bukti")
                }
            }
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                callback.onError(t.message.toString())
            }
        })
    }
}