package id.web.devin.mvckolam.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import id.web.devin.mvckolam.R
import id.web.devin.mvckolam.databinding.ActivityLoginBinding
import id.web.devin.mvckolam.databinding.ActivityPembelianDetailBinding

class PembelianDetailActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var b: ActivityPembelianDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityPembelianDetailBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.title = "Transaksi"
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostPembelianDetail) as NavHostFragment
        navController = navHostFragment.findNavController()
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

}