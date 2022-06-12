package com.bangkit.fica.presentation.splash

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.bangkit.fica.databinding.ActivitySplashBinding
import com.bangkit.fica.presentation.base.BaseActivity
import com.bangkit.fica.utils.extension.goToHome
import com.bangkit.fica.utils.extension.goToLogin
import com.bangkit.fica.utils.preferences.AuthPrefs

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val splashTimeout : Long = 1000

    override fun setLayout(): View {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        return binding.root
    }

    override fun initView() {
        super.initView()
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            if (AuthPrefs.hadLogin) {
                goToHome()
                finish()
            } else {
                goToLogin()
                finish()
            }
        }, splashTimeout)
    }
}