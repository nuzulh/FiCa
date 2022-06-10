package id.ajeng.storyapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ajeng.storyapp.R
import id.ajeng.storyapp.databinding.ActivityMainBinding
import id.ajeng.storyapp.service.data.StoryResults
import id.ajeng.storyapp.service.preferences.SettingsPreference
import id.ajeng.storyapp.ui.adapter.ListStoryAdapter
import id.ajeng.storyapp.utils.ViewModelFactory
import id.ajeng.storyapp.viewmodel.PreferencesViewModel
import id.ajeng.storyapp.viewmodel.StoryViewModel

class MainActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var binding: ActivityMainBinding
    private lateinit var storyViewModel: StoryViewModel
    private lateinit var prefViewModel : PreferencesViewModel
    private lateinit var adapter : ListStoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storyViewModel = ViewModelProvider(this)[StoryViewModel::class.java]
        val pref = SettingsPreference.getInstance(dataStore)
        prefViewModel = ViewModelProvider(this, ViewModelFactory(pref))[PreferencesViewModel::class.java]
        prefViewModel.getBearerToken().observe(this){
            val bearer = "Bearer $it"
            storyViewModel.getAllStory(bearer)
            showLoading(true)
        }

        adapter = ListStoryAdapter()
        adapter.setOnItemClick(object : ListStoryAdapter.OnItemClickCallback{
            override fun onItemClicked(data: StoryResults, optionsCompat: ActivityOptionsCompat) {
                intent = Intent(this@MainActivity, StoryDetailActivity::class.java)
                intent.putExtra(StoryDetailActivity.STORY_DETAIL, data)
                startActivity(intent, optionsCompat.toBundle())
            }
        })

        binding.apply {
            rvStory.layoutManager = LinearLayoutManager(this@MainActivity)
            rvStory.adapter = adapter
            rvStory.setHasFixedSize(true)

            storyViewModel.listStory.observe(this@MainActivity){
                showLoading(false)
                adapter.setData(it)
            }
        }

        binding.btnAddStory.setOnClickListener {
            intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.action_logout -> {
            prefViewModel.saveLoginState(false)
            prefViewModel.saveBearerToken("")
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}