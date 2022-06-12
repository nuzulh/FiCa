package com.bangkit.fica.presentation.authentification.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.bangkit.fica.databinding.ActivityRegisterBinding
import com.bangkit.fica.presentation.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun setLayout(): View {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
            btnRegister.setOnClickListener {
                checkInput()
            }

            btnLogin.setOnClickListener {
                finish()
            }
        }
    }

    private fun checkInput(){
        with(binding){
            val varFirstName    = edtName.text.toString().trim()
            val varEmail        = edtEmail.text.toString().trim()
            val varPassword     = edtPassword.text.toString().trim()

            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            when{
                TextUtils.isEmpty(varFirstName) -> edtName.error        = "Fill your name"
                TextUtils.isEmpty(varEmail)     -> edtEmail.error       = "Fill your email"
                TextUtils.isEmpty(varPassword)  -> edtPassword.error    = "Fill your password"
                else -> {
                    if (varEmail.matches(emailPattern.toRegex())) {
                        if (varPassword.length < 8){
                            edtPassword.error  = "Minimal 8 character"
                        } else {
                            showLoading(true)
                            signUp(varEmail, varPassword)
                        }
                    } else {
                        edtEmail.error = "Invalid email"
                    }
                }
            }
        }
    }

    private fun signUp(parEmail: String, parPassword: String){
        auth.createUserWithEmailAndPassword(parEmail, parPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showLoading(false)
                    Toast.makeText(this, "Register success, please login", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    showLoading(false)
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun showLoading(state: Boolean) {
        binding.progressCircular.isVisible = state
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        //email
        val textName = ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(500)
        val textInputName = ObjectAnimator.ofFloat(binding.tlName, View.ALPHA, 1f).setDuration(500)

        //email
        val textEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val textInputEmail =
            ObjectAnimator.ofFloat(binding.tlEmail, View.ALPHA, 1f).setDuration(500)

        //password
        val textPassword =
            ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val textInputPassword =
            ObjectAnimator.ofFloat(binding.tlPassword, View.ALPHA, 1f).setDuration(500)

        //button Login
        val btnRegister =
            ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)
        val registerLayout =
            ObjectAnimator.ofFloat(binding.layoutLogin, View.ALPHA, 1f).setDuration(500)


        val togetherName = AnimatorSet().apply {
            playTogether(textName, textInputName)
        }

        val togetherEmail = AnimatorSet().apply {
            playTogether(textEmail, textInputEmail)
        }

        val togetherPassword = AnimatorSet().apply {
            playTogether(textPassword, textInputPassword)
        }


        AnimatorSet().apply {
            playSequentially(
                togetherName,
                togetherEmail,
                togetherPassword,
                btnRegister,
                registerLayout
            )
            start()
        }
    }
}