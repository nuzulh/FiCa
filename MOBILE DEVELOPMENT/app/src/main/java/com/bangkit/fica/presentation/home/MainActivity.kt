package com.bangkit.fica.presentation.home

import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.bangkit.fica.R
import com.bangkit.fica.databinding.ActivityMainBinding
import com.bangkit.fica.presentation.base.BaseActivity
import com.bangkit.fica.presentation.home.fragment.NewsFragment
import com.bangkit.fica.presentation.home.fragment.ProfileFragment
import com.bangkit.fica.presentation.home.fragment.ScanFragment


class MainActivity : BaseActivity() {
    private lateinit var binding            : ActivityMainBinding

    override fun setLayout(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        return binding.root
    }

    override fun initView() {
        super.initView()
        val newsFragment    =NewsFragment()
        val scanFragment    =ScanFragment()
        val profileFragment =ProfileFragment()

        setCurrentFragment(scanFragment)

        binding.bottomNavigatinView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.scanFragment       ->setCurrentFragment(scanFragment)
                R.id.newsFragment       ->setCurrentFragment(newsFragment)
                R.id.profileFragment    ->setCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_fragment,fragment)
            commit()
        }
}
