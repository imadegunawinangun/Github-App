package com.rumahgugun.consumerapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rumahgugun.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserSearchAdapter
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserSearchAdapter()
        adapter.notifyDataSetChanged()

        viewModel= ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.setListUser(this)
        viewModel.getListUser().observe(this,{
            if (it!=null){
                adapter.setList(it)
                binding.tvFound.visibility = View.VISIBLE
                val getTotalUser = viewModel.getTotalUser()
                binding.tvFound.text = textTemp(
                    "$getTotalUser ${getString(R.string.user)} - ${getString(R.string.showing)} ${it.size} ${
                        getString(R.string.user)
                    }"
                )

            }})

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(false)
        binding.recyclerView.adapter = adapter



    }
    private fun textTemp(string: String):String{
        return string
    }
}