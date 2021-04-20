package com.rumahgugun.github.activity.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rumahgugun.github.R
import com.rumahgugun.github.data.UserDetail
import com.rumahgugun.github.databinding.FragmentFollowBinding
import com.rumahgugun.github.activity.main.UserSearchAdapter
import com.rumahgugun.github.other.LoadingScreen
import com.rumahgugun.github.other.ViewModel


class FollowersFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ViewModel
    private lateinit var adapter: UserSearchAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)

        val arg = arguments
        username = arg?.getString(DetailActivity.EXTRA_USERNAME).toString()

        adapter = UserSearchAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserSearchAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserDetail) {
                val moveWithObjectIntent = Intent(activity, DetailActivity::class.java)
                moveWithObjectIntent.putExtra(DetailActivity.EXTRA_USER, user)
                startActivity(moveWithObjectIntent)
            }
        })

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter

        LoadingScreen().loadingScreen(true, binding.progressBar)
        viewModel = ViewModelProvider(
            this
        ).get(ViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListUser().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
            }
            LoadingScreen().loadingScreen(false, binding.progressBar)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}