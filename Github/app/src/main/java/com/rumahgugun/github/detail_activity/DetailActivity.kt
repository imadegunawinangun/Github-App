package com.rumahgugun.github.detail_activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rumahgugun.github.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.title = "Details"

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        if (username != null) {
            viewModel.setUserDetail(username)
        }
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    tvNameReceived.text = it.name
                    tvUsernameReceived.text = it.login
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .into(imgReceived)
                    if (it.company == null) {
                        tvCompanyReceived.visibility = View.GONE
                    } else {
                        tvCompanyReceived.text = it.company
                    }
                    if (it.company == null) {
                        tvLocationReceived.visibility = View.GONE
                    } else {
                        tvLocationReceived.text = it.location
                    }
                    tvFollower.text = "${it.followers.toString()} follower"
                    tvFollowing.text = "${it.following.toString()} following"
                    tvRepository.text = "${it.public_repos.toString()} repository"
                }
            }
        })

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }

    }
}