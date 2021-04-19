package com.rumahgugun.github.activity.listfavorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rumahgugun.github.R
import com.rumahgugun.github.activity.detail.DetailActivity
import com.rumahgugun.github.activity.main.UserSearchAdapter
import com.rumahgugun.github.data.UserDetail
import com.rumahgugun.github.databinding.ActivityListFavoriteBinding
import com.rumahgugun.github.other.Other

class ListFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListFavoriteBinding
    private lateinit var adapter: UserSearchAdapter
    private lateinit var viewModel: ListFavoriteViewModel
    private fun textTemp(string: String):String = Other().textTemp(string)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserSearchAdapter()
        adapter.notifyDataSetChanged()

        viewModel= ViewModelProvider(this).get(ListFavoriteViewModel::class.java)

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
}