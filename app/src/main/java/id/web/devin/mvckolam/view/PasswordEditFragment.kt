package id.web.devin.mvckolam.view

import android.app.AlertDialog
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import id.web.devin.mvckolam.R
import id.web.devin.mvckolam.controller.ProfileController
import id.web.devin.mvckolam.databinding.FragmentPasswordEditBinding
import id.web.devin.mvckolam.model.Pengguna
import id.web.devin.mvckolam.util.EncryptionUtils
import id.web.devin.mvckolam.util.Global
import id.web.devin.mvckolam.util.ProfilView

class PasswordEditFragment : Fragment(),ProfilView {
    private lateinit var b:FragmentPasswordEditBinding
    private lateinit var cProfil:ProfileController
    private var pwd:String = ""
    private var newPwd:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        cProfil = ProfileController(this.requireContext(),this)
        b = FragmentPasswordEditBinding.inflate(layoutInflater)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = context?.let { Global.getEmail(it) }
        val encryptPwd = EncryptionUtils.encrypt(pwd)
        b.btnSimpanEditPwd.setOnClickListener {
            pwd = b.txtEditKataSandi.text.toString()
            newPwd = b.txtEditKonfirmasiKataSandi.text.toString()
            if(pwd.isNotEmpty() && newPwd.isNotEmpty()){
                if (pwd.equals(newPwd)){
                    if(pwd.length >= 4 && pwd.length <=8){
                        cProfil.updateKatasandi(email.toString(),encryptPwd)
                    }else{
                        Toast.makeText(context, "Kata Sandi Harus Menggunakan 4-8 Karakter!", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    AlertDialog.Builder(context).apply {
                        val title = SpannableString("Peringatan")
                        title.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, title.length, 0)
                        val message = SpannableString("Kata Sandi Tidak Cocok Dengan Konfimasi Kata Sandi")
                        message.setSpan(
                            AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0,
                            message.length,
                            0
                        )
                        setTitle(title)
                        setMessage(message)
                        setPositiveButton("OK", null)
                        create().show()
                    }
                }
            }else{
                AlertDialog.Builder(context).apply {
                    val title = SpannableString("Peringatan")
                    title.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, title.length, 0)
                    val message = SpannableString("Data Tidak Boleh Kosong")
                    message.setSpan(
                        AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0,
                        message.length,
                        0
                    )
                    setTitle(title)
                    setMessage(message)
                    setPositiveButton("OK", null)
                    create().show()
                }
            }
        }
        b.btnBatalEditKataSandi.setOnClickListener {
            AlertDialog.Builder(context).apply {
                val title = SpannableString("Peringatan")
                title.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, title.length, 0)
                val message = SpannableString("Batal Melakukan Perubahan?")
                message.setSpan(
                    AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                    0,
                    message.length,
                    0
                )
                setTitle(title)
                setMessage(message)
                setPositiveButton("BATAL"){ dialog,_->
                    val action = PasswordEditFragmentDirections.actionEditPwdToItemDataDiri()
                    Navigation.findNavController(it).navigate(action)
                }
                setNegativeButton("TIDAK"){ dialog,_->
                    dialog.dismiss()
                }
                create().show()
            }
        }
    }

    override fun showError(message: String) {
        AlertDialog.Builder(context).apply {
            val message = SpannableString("Gagal Mengubah Kata Sandi")
            message.setSpan(
                AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0,
                message.length,
                0
            )
            setMessage(message)
            setPositiveButton("OK", null)
            create().show()
        }
    }

    override fun success() {
        AlertDialog.Builder(context).apply {
            val message = SpannableString("Berhasil Mengubah Kata Sandi")
            message.setSpan(
                AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0,
                message.length,
                0
            )
            setMessage(message)
            setPositiveButton("OK") { _, _ ->
                val action = PasswordEditFragmentDirections.actionEditPwdToItemDataDiri()
                findNavController().navigate(action)
            }
            create().show()
        }
    }

    override fun showProfil(profile: List<Pengguna>) {}
}