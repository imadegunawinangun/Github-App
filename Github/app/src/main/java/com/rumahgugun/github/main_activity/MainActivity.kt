package com.rumahgugun.github.main_activity


import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rumahgugun.github.R
import com.rumahgugun.github.data.UserDetail
import com.rumahgugun.github.databinding.ActivityMainBinding
import com.rumahgugun.github.detail_activity.DetailActivity
import com.rumahgugun.github.other.LoadingScreen
import com.rumahgugun.github.other.Other


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.title = "Github User Finder"
        adapter = UserSearchAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserSearchAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserDetail) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USER, user)
                    startActivity(it)
                }
            }
        })


        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(false)
        binding.recyclerView.adapter = adapter

        binding.icSearch.setOnClickListener {
            LoadingScreen().loadingScreen(true, binding.progressBar)
            if(searchUser()){
                closeTextView(true)
            }else{
                closeTextView(false)
                LoadingScreen().loadingScreen(false, binding.progressBar)
                binding.tvFound.visibility = View.INVISIBLE
            }
            LoadingScreen().loadingScreen(false, binding.progressBar)

        }

        binding.etQuery.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                LoadingScreen().loadingScreen(true, binding.progressBar)
                if(searchUser()){
                    closeTextView(true)
                }else{
                    closeTextView(false)
                    LoadingScreen().loadingScreen(false, binding.progressBar)
                    binding.tvFound.visibility = View.INVISIBLE
                }
                return@setOnKeyListener true

            }
            return@setOnKeyListener false
        }

        viewModel.getSearchUser().observe(this, {
            adapter.setList(it)
            binding.recyclerView.visibility = View.VISIBLE
            if (it != null) {
                val getTotalUser = viewModel.getTotalUser()
                if (adapter.itemCount != 0) {
                    binding.tvFound.text = Other().textTemp("${getTotalUser.toString()} ${getString(R.string.user)} - ${getString(R.string.showing)} ${it.size} ${getString(R.string.user)}")
                } else {
                    closeTextView(false)
                    binding.tvFound.text = getString(R.string.user_not_found)
                }
                binding.tvFound.visibility = View.VISIBLE
            }
            LoadingScreen().loadingScreen(false, binding.progressBar)
        })
    }

    private fun closeTextView(b: Boolean) = if (b) {
        binding.textView.visibility = View.GONE
    } else {
        binding.textView.visibility = View.VISIBLE
    }

    private fun searchUser(): Boolean {
        val query = binding.etQuery.text.toString()
        return if (query.isEmpty()) {
            binding.recyclerView.visibility = View.INVISIBLE
            false
        }else{
            viewModel.setSearchUser(query)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
/*
            R.id.favorite_page -> startActivity(Intent(this, FavoriteActivity::class.java))
*/
            R.id.language_settings -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
/*
            R.id.alarm_settings -> startActivity(Intent(this, AlarmActivity::class.java))
*/
        }
        return super.onOptionsItemSelected(item)
    }
}