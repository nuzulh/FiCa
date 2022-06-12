package com.bangkit.fica.utils.extension

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import com.bangkit.fica.R
import com.bangkit.fica.presentation.authentification.login.LoginActivity
import com.bangkit.fica.presentation.authentification.register.RegisterActivity
import com.bangkit.fica.presentation.home.MainActivity

fun Activity.goToHome() {
    startActivity(
        Intent(
            this,
            MainActivity::class.java
        )
    )
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

fun Activity.goToLogin() {
    startActivity(
        Intent(
            this,
            LoginActivity::class.java
        )
    )
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

fun Activity.goToRegister() {
    startActivity(
        Intent(
            this,
            RegisterActivity::class.java
        )
    )
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

fun Activity.goToCamera() {
    startActivityForResult(
        Intent(MediaStore.ACTION_IMAGE_CAPTURE),
        10
    )
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}