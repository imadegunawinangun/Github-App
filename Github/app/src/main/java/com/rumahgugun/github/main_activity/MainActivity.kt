package com.rumahgugun.github.main_activity


import android.annotation.SuppressLint
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


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserSearchAdapter

    @SuppressLint("SetTextI18n")
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
                    it.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
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
            closeTextView(true)
            searchUser()
        }

        binding.etQuery.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                closeTextView(true)
                searchUser()
                return@setOnKeyListener true

            }
            return@setOnKeyListener false
        }

        viewModel.getSearchUser().observe(this, {
            if (it != null) {
                adapter.setList(it)
                binding.tvFound.visibility = View.VISIBLE
                if (it.size == 1) {
                    binding.tvFound.text = "showing ${it.size} user"
                } else {
                    binding.tvFound.text = "showing ${it.size} users"
                }
                LoadingScreen().loadingScreen(false, binding.progressBar)
            } else {
                closeTextView(false)

            }
        })
    }

    private fun closeTextView(b: Boolean) = if (b) {
        binding.textView.visibility = View.GONE
    } else {
        binding.textView.visibility = View.VISIBLE

    }

    private fun searchUser() {
        val query = binding.etQuery.text.toString()
        if (query.isEmpty()) {
            binding.recyclerView.visibility = View.INVISIBLE
            closeTextView(false)
            binding.tvFound.visibility = View.GONE
            return
        }
        viewModel.setSearchUser(query)
        LoadingScreen().loadingScreen(true, binding.progressBar)
        binding.recyclerView.visibility = View.VISIBLE

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.language_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}