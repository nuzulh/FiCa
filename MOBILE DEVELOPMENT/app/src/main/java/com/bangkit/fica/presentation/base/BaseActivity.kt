package com.bangkit.fica.presentation.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        handleIntentData()
        handleAppBar()
        initObserver()
        initClickListener()
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDestroyActivity()
    }

    abstract fun setLayout(): View

    open fun handleAppBar() {}

    open fun onDestroyActivity() {}

    open fun initView() {}

    open fun initData() {}

    open fun handleIntentData() {}

    open fun initObserver() {}

    open fun initClickListener() {}

}