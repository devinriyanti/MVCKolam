package id.web.devin.mvckolam.controller

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import id.web.devin.mvckolam.model.Kolam
import id.web.devin.mvckolam.model.Pelatih
import id.web.devin.mvckolam.model.Produk
import id.web.devin.mvckolam.model.Tiket
import id.web.devin.mvckolam.util.KolamDetailControllerListener
import org.json.JSONArray
import org.json.JSONObject

class KolamDetailController(private val context: Context, private val listener: KolamDetailControllerListener) {
    private val TAG = "volleyTAG"
    private var queue: RequestQueue?= null

    fun fetchDetailKolam(idKolam:String){
        queue = Volley.newRequestQueue(context)
        var url = "https://lokowai.shop/kolamdetail.php?id=$idKolam"
        val stringReq = StringRequest(Request.Method.GET,url,
            {response->
                Log.d("successKolam", response.toString())
                val kolam = parseKolam(response)
                Log.d("success", kolam.toString())
                listener.showKolam(kolam)
            },
            {error->
                Log.d("successKolam", error.toString())
                listener.showError(error.toString())
            }
        ).apply {
            tag = "TAG"
        }
        queue?.add(stringReq)
    }

    private fun parseKolam(response: String?): List<Kolam> {
        val kolams = mutableListOf<Kolam>()
        try {
            val kolamJSON = JSONObject(response)
            val id = kolamJSON.getString("id")
            val nama = kolamJSON.getString("nama")
            val alamat = kolamJSON.getString("alamat")
            val deskripsi = kolamJSON.getString("deskripsi")
            val gambarUrl = kolamJSON.getString("gambar")
            val isMaintenance = kolamJSON.getString("is_maintenance")
            val kota = kolamJSON.getString("kota")
            val lokasi = kolamJSON.getString("lokasi")
            val admin = kolamJSON.getString("email_pengguna")

            val produkArray = kolamJSON.getJSONArray("product")
            val produkList = parseProdukList(produkArray)

            val tiketArray = kolamJSON.getJSONArray("tiket")
            val tiketList = parseTiketList(tiketArray)

            val pelatihArray = kolamJSON.getJSONArray("pelatih")
            val pelatihList = parsePelatihList(pelatihArray)

            val kolam = Kolam(id, nama, alamat, deskripsi, gambarUrl, isMaintenance,
                kota, lokasi, admin, produkList as ArrayList<Produk>,
                tiketList as ArrayList<Tiket>, pelatihList as ArrayList<Pelatih>
            )
            kolams.add(kolam)
        } catch (e: Exception) {
            // Handle JSON parsing error
            listener.showError("Error parsing products")
        }
        return kolams
    }

    private fun parseTiketList(array: JSONArray): List<Tiket> {
        val tiketList = mutableListOf<Tiket>()
        for (i in 0 until array.length()) {
            val tiketJSON: JSONObject = array.getJSONObject(i)
            val idproduk = tiketJSON.getString("id")
            val idkolam = tiketJSON.getString("idkolam")
            val nama = tiketJSON.getString("nama")
            val kota = tiketJSON.getString("kota")
            val deskripsi = tiketJSON.getString("deskripsi")
            val qty = tiketJSON.optDouble("kuantitas")
            val harga = tiketJSON.optDouble("harga")
            val diskon = tiketJSON.optDouble("diskon")
            val gambarUrl = tiketJSON.getString("gambar")
            val berat = tiketJSON.optDouble("berat")

            val tiket = Tiket(idproduk, idkolam, nama, kota, deskripsi, qty, harga, diskon, gambarUrl, berat)
            tiketList.add(tiket)
        }
        return tiketList
    }

    private fun parsePelatihList(array: JSONArray): List<Pelatih> {
        val pelatihList = mutableListOf<Pelatih>()
        for (i in 0 until array.length()) {
            val pelatihJSON: JSONObject = array.getJSONObject(i)
            val id = pelatihJSON.getString("id")
            val nama = pelatihJSON.getString("nama")
            val tglLahir = pelatihJSON.getString("tanggal_lahir")
            val kontak = pelatihJSON.getString("kontak")
            val tglKarir = pelatihJSON.getString("mulai_karir")
            val deskripsi = pelatihJSON.getString("deskripsi")
            val jenisKelamin = pelatihJSON.getString("jenis_kelamin")
            val gambarUrl = pelatihJSON.getString("gambar")

            val pelatih = Pelatih(id, nama, tglLahir, kontak, tglKarir, deskripsi, jenisKelamin, gambarUrl)
            pelatihList.add(pelatih)
        }
        return pelatihList
    }

    private fun parseProdukList(array: JSONArray): List<Produk> {
        val produkList = mutableListOf<Produk>()
        for (i in 0 until array.length()) {
            val produkJSON: JSONObject = array.getJSONObject(i)
            val idproduk = produkJSON.getString("id")
            val kolam = produkJSON.getString("idkolam")
            val nama = produkJSON.getString("nama")
            val kota = produkJSON.getString("kota")
            val deskripsi = produkJSON.getString("deskripsi")
            val qty = produkJSON.optInt("kuantitas")
            val harga = produkJSON.optDouble("harga")
            val diskon = produkJSON.optDouble("diskon")
            val gambarUrl = produkJSON.getString("gambar")
            val berat = produkJSON.optDouble("berat")

            val produk = Produk(idproduk, kolam, nama, kota, deskripsi, qty, harga, diskon, gambarUrl, berat)
            produkList.add(produk)
        }

        return produkList
    }
}