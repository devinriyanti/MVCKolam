package id.web.devin.mvckolam.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.web.devin.mvckolam.controller.TransaksiController
import id.web.devin.mvckolam.databinding.FragmentDiprosesBinding
import id.web.devin.mvckolam.model.Role
import id.web.devin.mvckolam.model.StatusTransaksi
import id.web.devin.mvckolam.model.Transaction
import id.web.devin.mvckolam.util.Global
import id.web.devin.mvckolam.util.TransaksiView
import id.web.devin.mvvmkolam.view.PembelianListAdapter

class DiprosesFragment : Fragment(), TransaksiView {
    private lateinit var b:FragmentDiprosesBinding
    private lateinit var pembelianListAdapter: PembelianListAdapter
    private lateinit var cTransaksi: TransaksiController
    lateinit var email:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        cTransaksi = TransaksiController(this.requireContext(),this)
        b = FragmentDiprosesBinding.inflate(layoutInflater)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.txtErrorDiproses.visibility = View.GONE
        email = context?.let { Global.getEmail(it) }.toString()
        pembelianListAdapter = PembelianListAdapter(arrayListOf())
        val role = Global.getRole(requireContext())
        if(role == Role.Admin.toString()){
            cTransaksi.fetchTransaksiAdmin(email, StatusTransaksi.Diproses.name)
        }else{
            cTransaksi.fetchTransaksi(email, StatusTransaksi.Diproses.name)
        }
    }

    override fun showError(message: String) {
        if(message == "Tidak Ada Transaksi"){
            b.txtStatusDiproses.text = message
        }else{
            b.txtErrorDiproses.visibility = View.VISIBLE
        }
        Log.d("eror",message)
        b.progressBarDiproses.visibility = View.GONE
    }

    override fun showTransaksi(transaksi: List<Transaction>) {
        transaksi.forEach {
            if(it.id != "null"){
                pembelianListAdapter.updateTransactionList(transaksi)
                b.recViewDiproses.layoutManager = LinearLayoutManager(context)
                b.recViewDiproses.adapter = pembelianListAdapter
                b.progressBarDiproses.visibility = View.GONE
            }else{
                b.txtStatusDiproses.text = "Tidak Ada Transaksi"
                b.progressBarDiproses.visibility = View.GONE
            }
        }
    }

    override fun success() {}
}