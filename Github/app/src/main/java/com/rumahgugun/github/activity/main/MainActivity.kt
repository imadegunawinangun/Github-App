package com.rumahgugun.github.activity.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rumahgugun.github.R
import com.rumahgugun.github.activity.alarm.AlarmActivity
import com.rumahgugun.github.data.UserDetail
import com.rumahgugun.github.databinding.ActivityMainBinding
import com.rumahgugun.github.activity.detail.DetailActivity
import com.rumahgugun.github.activity.favorite.ListFavoriteActivity
import com.rumahgugun.github.other.LoadingScreen
import com.rumahgugun.github.other.Other
import com.rumahgugun.github.other.ViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel
    private lateinit var adapter: UserSearchAdapter

    private fun loadingScreen(b: Boolean, progressBar: ProgressBar) =
        LoadingScreen().loadingScreen(b, progressBar)

    private fun textTemp(string: String) = Other().textTemp(string)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        val username = intent.getStringExtra(EXTRA_USERNAME)

        viewModel = ViewModelProvider(
            this
        ).get(ViewModel::class.java)
        binding.tvFound.visibility = View.INVISIBLE
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(false)
        binding.recyclerView.adapter = adapter
        binding.icSearch.setOnClickListener {
            binding.recyclerView.visibility = View.INVISIBLE
            binding.tvFound.visibility = View.INVISIBLE
            loadingScreen(true, binding.progressBar)
            if (searchUser()) {
                closeTextView(true)
            } else {
                closeTextView(false)
                loadingScreen(false, binding.progressBar)
            }
        }

        if (username != null) {
            viewModel.setListUser(username)
            closeTextView(true)
        }

        binding.etQuery.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.recyclerView.visibility = View.INVISIBLE
                binding.tvFound.visibility = View.INVISIBLE
                loadingScreen(true, binding.progressBar)
                if (searchUser()) {
                    closeTextView(true)
                } else {
                    closeTextView(false)
                    loadingScreen(false, binding.progressBar)
                }
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        viewModel.getListUser().observe(this, {
            adapter.setList(it)
            binding.recyclerView.visibility = View.VISIBLE
            if (it != null) {
                if (adapter.itemCount != 0) {
                    binding.tvFound.visibility = View.VISIBLE
                    val getTotalUser = viewModel.getTotalUser()
                    binding.tvFound.text = textTemp(
                        "$getTotalUser ${getString(R.string.user)} - ${getString(R.string.showing)} ${it.size} ${
                            getString(R.string.user)
                        }"
                    )
                } else {
                    closeTextView(false)
                    binding.tvFound.text = getString(R.string.user_not_found)
                }
            }
            loadingScreen(false, binding.progressBar)
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
        } else {
            viewModel.setListUser(query)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_page -> {
                startActivity(Intent(this, ListFavoriteActivity::class.java))
            }
            R.id.language_settings -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            R.id.alarm_settings -> {
                startActivity(Intent(this, AlarmActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}