package com.rumahgugun.github.detail_activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rumahgugun.github.data.UserDetail
import com.rumahgugun.github.databinding.ActivityDetailBinding
import com.rumahgugun.github.other.Other

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var username:String
    private lateinit var userDetail:UserDetail
    private fun textTemp(string: String): String = Other().textTemp(string)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDetail = intent.getParcelableExtra<UserDetail>(EXTRA_USER) as UserDetail
        username = userDetail.login

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        title = "$username Details"


        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

            viewModel.setUserDetail(username)

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
                    tvFollower.text = textTemp("${it.followers.toString()} follower")
                    tvFollowing.text = textTemp("${it.following.toString()} following")
                    tvRepository.text = textTemp("${it.public_repos.toString()} repository")
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