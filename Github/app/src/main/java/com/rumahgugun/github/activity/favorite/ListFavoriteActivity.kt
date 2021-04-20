package com.rumahgugun.github.activity.favorite

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rumahgugun.github.R
import com.rumahgugun.github.activity.alarm.AlarmActivity
import com.rumahgugun.github.activity.detail.DetailActivity
import com.rumahgugun.github.activity.main.MainActivity
import com.rumahgugun.github.activity.main.UserSearchAdapter
import com.rumahgugun.github.data.UserDetail
import com.rumahgugun.github.databinding.ActivityListFavoriteBinding
import com.rumahgugun.github.other.Other
import com.rumahgugun.github.other.ViewModel

class ListFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListFavoriteBinding
    private lateinit var adapter: UserSearchAdapter
    private lateinit var viewModel: ViewModel
    private fun textTemp(string: String):String = Other().textTemp(string)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserSearchAdapter()
        adapter.notifyDataSetChanged()

        viewModel= ViewModelProvider(this).get(ViewModel::class.java)

        adapter.setOnItemClickCallback(object : UserSearchAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserDetail) {
                Intent(this@ListFavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USER, user)
                    startActivity(it)
                }
            }
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(false)
        binding.recyclerView.adapter = adapter

        viewModel.getFavoriteUser()?.observe(this,{
            adapter.setList(it)
            binding.tvFound.visibility = View.VISIBLE
            binding.tvFound.text = textTemp(it.size.toString() + " " + getString(R.string.user))
            binding.textView.visibility = View.GONE
            if(it.isEmpty()){
                binding.textView.visibility = View.VISIBLE
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_sub, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.hint_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                Intent(this@ListFavoriteActivity, MainActivity::class.java).also {
                    it.putExtra(MainActivity.EXTRA_USERNAME, query)
                    startActivity(it)
                    return true
                }
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_page ->{
                startActivity(Intent(this, ListFavoriteActivity::class.java))
                finish()
            }
            R.id.language_settings ->{
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                finish()
            }
            R.id.alarm_settings ->{
                startActivity(Intent(this, AlarmActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}