package com.bangkit.fica.presentation.authentification.login

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.bangkit.fica.databinding.ActivityLoginBinding
import com.bangkit.fica.presentation.base.BaseActivity
import com.bangkit.fica.utils.extension.goToHome
import com.bangkit.fica.utils.extension.goToRegister
import com.bangkit.fica.utils.preferences.AuthPrefs
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private var permissionsRequired = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun setLayout(): View {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        return binding.root
    }

    override fun initData() {
        super.initData()
        auth = FirebaseAuth.getInstance()
    }

    override fun initView() {
        super.initView()
        supportActionBar?.hide()
        playAnimation()
    }

    override fun initClickListener() {
        super.initClickListener()
        with(binding){
            btnLogin.setOnClickListener {
                requestPermission()
            }

            btnRegister.setOnClickListener {
                goToRegister()
            }
        }
    }

    private fun checkInput(){
        with(binding){
            val varEmail        = edtEmail.text.toString().trim()
            val varPassword     = edtPassword.text.toString().trim()
            when{
                TextUtils.isEmpty(varEmail)     -> edtEmail.error     = "Fill your email"
                TextUtils.isEmpty(varPassword)  -> edtPassword.error  = "Fill your password"
                else -> {
                    showLoading(true)
                    logIn(varEmail, varPassword)
                }
            }
        }
    }

    private fun logIn(parEmail: String, parPassword: String){
        auth.signInWithEmailAndPassword(parEmail, parPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showLoading(false)
                    goToHome()
                    AuthPrefs.hadLogin  = true
                    AuthPrefs.email     = parEmail
                    finish()
                } else {
                    showLoading(false)
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        //email
        val textEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val textInputEmail = ObjectAnimator.ofFloat(binding.tlEmail, View.ALPHA, 1f).setDuration(500)

        //password
        val textPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val textInputPassword = ObjectAnimator.ofFloat(binding.tlPassword, View.ALPHA, 1f).setDuration(500)

        //button Login
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val registerLayout = ObjectAnimator.ofFloat(binding.layoutRegister, View.ALPHA, 1f).setDuration(500)

        val togetherEmail = AnimatorSet().apply {
            playTogether(textEmail, textInputEmail)
        }

        val togetherPassword = AnimatorSet().apply {
            playTogether(textPassword, textInputPassword)
        }

        AnimatorSet().apply {
            playSequentially(
                togetherEmail,
                togetherPassword,
                btnLogin,
                registerLayout
            )
            start()
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressCircular.isVisible = state
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 5) {
            var allgranted = false
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true
                } else {
                    allgranted = false
                    break
                }
            }
            if (allgranted) {
                checkInput()
            } else {
                Toast.makeText(applicationContext, "Unable to get Permission", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[1])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[2])) {
                Toast.makeText(this, "Please Grant Camera and Storage Permission", Toast.LENGTH_LONG).show()
            } else {
                ActivityCompat.requestPermissions(this, permissionsRequired, 5)
            }
        } else {
            checkInput()
        }
    }

}