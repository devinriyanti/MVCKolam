package id.web.devin.mvckolam.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.web.devin.mvckolam.controller.CartController
import id.web.devin.mvckolam.databinding.FragmentKeranjangListBinding
import id.web.devin.mvckolam.model.Cart
import id.web.devin.mvckolam.model.ProdukCart
import id.web.devin.mvckolam.util.CartView
import id.web.devin.mvckolam.util.Global
import id.web.devin.mvvmkolam.view.CartItemAdapter
import id.web.devin.mvvmkolam.view.CartListAdapter

class KeranjangListFragment : Fragment(),CartView, CartItemAdapter.CartItemListener {
    private lateinit var b:FragmentKeranjangListBinding
    private lateinit var cCart:CartController
    private lateinit var cartListAdapter: CartListAdapter
    lateinit var email:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        cCart = CartController(this.requireContext(),this)
        b = FragmentKeranjangListBinding.inflate(layoutInflater)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.txtErrorCart.visibility = View.GONE
        cartListAdapter = CartListAdapter(requireContext(), arrayListOf(), this)
        email = context?.let { Global.getEmail(it) }.toString()
        b.txtErrorCart.visibility = View.GONE
        cCart.fetchCart(email)
    }

    override fun showError(message: String) {
        if(message.equals("Keranjang Kosong")){
            b.textIsiKeranjang.text = message
        }else if(message.equals("Kosong")){
            cCart.fetchCart(email)
        }else{
            b.txtErrorCart.visibility = View.VISIBLE
        }
        Log.d("error", message)
        b.progressBarCart.visibility = View.GONE
    }

    override fun showCart(cart: List<Cart>) {
        Log.d("cartLD",cart.toString())
        b.recyclerViewCart.visibility = View.VISIBLE
        b.progressBarCart.visibility = View.GONE
        cartListAdapter.updateCartList(cart)

        b.refreshCart.setOnRefreshListener {
            b.recyclerViewCart.visibility = View.GONE
            b.txtErrorCart.visibility = View.GONE
            b.textIsiKeranjang.visibility = View.GONE
            b.progressBarCart.visibility = View.VISIBLE
            cCart.fetchCart(email)
            b.refreshCart.isRefreshing = false
        }
        b.recyclerViewCart.layoutManager = LinearLayoutManager(context)
        b.recyclerViewCart.adapter = cartListAdapter
    }

    override fun success() {}

    override fun onRemoveClicked(cartItem: ProdukCart) {
        cCart.removeCart(cartItem.idkeranjangs, cartItem.idproduk)
        cCart.fetchCart(email)
    }

    var qty: Int = 0
    override fun onDecreaseClicked(cartItem: ProdukCart) {
        qty = cartItem.qty - 1
        if(qty >= 1){
            cCart.updateQty(qty, cartItem.idkeranjangs, cartItem.idproduk)
        }else if(qty < 1){
            cCart.removeCart(cartItem.idkeranjangs, cartItem.idproduk)
        }
        cCart.fetchCart(email)
    }

    override fun onIncreaseClicked(cartItem: ProdukCart) {
        qty = cartItem.qty + 1
        cCart.updateQty(qty, cartItem.idkeranjangs, cartItem.idproduk)
        cCart.fetchCart(email)
    }
}