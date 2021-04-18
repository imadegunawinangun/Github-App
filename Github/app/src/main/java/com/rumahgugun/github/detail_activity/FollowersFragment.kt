package com.rumahgugun.github.detail_activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rumahgugun.github.R
import com.rumahgugun.github.databinding.FragmentFollowBinding
import com.rumahgugun.github.main_activity.UserSearchAdapter
import com.rumahgugun.github.other.LoadingScreen


class FollowersFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserSearchAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)

        val arg = arguments
        username = arg?.getString(DetailActivity.EXTRA_USERNAME).toString()

        adapter = UserSearchAdapter()
        adapter.notifyDataSetChanged()

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter

        LoadingScreen().loadingScreen(true, binding.progressBar)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollower().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                LoadingScreen().loadingScreen(false, binding.progressBar)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}