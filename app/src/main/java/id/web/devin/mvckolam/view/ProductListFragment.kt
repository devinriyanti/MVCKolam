package id.web.devin.mvckolam.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import id.web.devin.mvckolam.controller.KolamDetailController
import id.web.devin.mvckolam.databinding.FragmentProductListBinding
import id.web.devin.mvckolam.model.Kolam
import id.web.devin.mvckolam.model.Role
import id.web.devin.mvckolam.util.Global
import id.web.devin.mvckolam.util.KolamDetailView
import id.web.devin.mvvmkolam.view.ProductListAdapter

class ProductListFragment : Fragment(), KolamDetailView {
    private lateinit var b:FragmentProductListBinding
    private lateinit var cKolamDetail: KolamDetailController
    private lateinit var productListAdapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        cKolamDetail = KolamDetailController(this.requireContext(),this)
        b = FragmentProductListBinding.inflate(layoutInflater)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val role = context?.let { Global.getRole(it) }
        b.txtErorProduk.visibility = View.GONE
//        b.txtErorProduk.visibility = View.GONE
        val sharedPreferences = requireActivity().getSharedPreferences("kolam", Context.MODE_PRIVATE)
        val id = sharedPreferences.getString("id", null)
        cKolamDetail.fetchDetailKolam(id.toString())
        productListAdapter = ProductListAdapter(requireContext(),arrayListOf())

        b.recViewProduct.layoutManager = GridLayoutManager(context, 2)
        b.recViewProduct.adapter = productListAdapter

        if(role == Role.Admin.name){
            b.fabTambahProduk.visibility = View.VISIBLE

            b.fabTambahProduk.setOnClickListener{
                val action = KolamDetailFragmentDirections.actionProductAddFragment()
                Navigation.findNavController(it).navigate(action)
            }
        }
        b.refreshLayoutProduct.setOnRefreshListener {
            b.recViewProduct.visibility = View.GONE
            b.txtErorProduk.visibility = View.GONE
            b.progressLoadProduk.visibility = View.VISIBLE
            cKolamDetail.fetchDetailKolam(id.toString())
            b.refreshLayoutProduct.isRefreshing = false
        }
    }

    override fun showError(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        b.progressLoadProduk.visibility = View.GONE
    }

    override fun showKolam(kolam: List<Kolam>) {
        b.progressLoadProduk.visibility = View.GONE
        kolam.forEach {
            if(!it.produk.isNullOrEmpty()){
                productListAdapter.updateProductList(it.produk)
            }else{
                b.txtProdukStok.setText("Tidak Ada Produk")
            }
        }
    }
}