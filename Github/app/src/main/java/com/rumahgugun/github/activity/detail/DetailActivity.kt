package com.rumahgugun.github.activity.detail

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rumahgugun.github.R
import com.rumahgugun.github.activity.alarm.AlarmActivity
import com.rumahgugun.github.activity.listfavorite.ListFavoriteActivity
import com.rumahgugun.github.data.UserDetail
import com.rumahgugun.github.databinding.ActivityDetailBinding
import com.rumahgugun.github.other.Other
import com.rumahgugun.github.other.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: ViewModel
    private lateinit var username: String
    private lateinit var userDetail: UserDetail
    private fun textTemp(string: String): String = Other().textTemp(string)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDetail = intent.getParcelableExtra<UserDetail>(EXTRA_USER) as UserDetail
        username = userDetail.login
        val id = userDetail.id
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        title = "$username Details"


        viewModel = ViewModelProvider(
                this
        ).get(ViewModel::class.java)

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

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleButton.isChecked = true
                        isChecked = true
                    } else {
                        binding.toggleButton.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.toggleButton.setOnClickListener{
            isChecked = !isChecked
            if(isChecked)
            {
                viewModel.addToFavorite(userDetail)
            } else{
                viewModel.removeFromFavorite(id)
            }
            binding.toggleButton.isChecked = isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
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