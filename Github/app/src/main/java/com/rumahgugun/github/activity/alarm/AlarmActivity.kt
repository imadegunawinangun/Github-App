package com.rumahgugun.github.activity.alarm

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.rumahgugun.github.R
import com.rumahgugun.github.activity.favorite.ListFavoriteActivity
import com.rumahgugun.github.activity.main.MainActivity
import com.rumahgugun.github.databinding.ActivityAlarmBinding
import com.rumahgugun.github.other.IsReminded
import com.rumahgugun.github.other.ViewModel
import com.rumahgugun.github.preference.IsRemindedPreference
import com.rumahgugun.github.receiver.AlarmReceiver

class AlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding
    private lateinit var isReminded: IsReminded
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alarmPreference = IsRemindedPreference(this)
        if(alarmPreference.getReminder().isReminded){
            binding.switch1.isChecked = true
        } else{
            binding.switch1.isChecked = false
        }

        alarmReceiver = AlarmReceiver()
        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                saveReminder(true)
                alarmReceiver.setRepeatingAlarm(this, "RepeatingAlarm", "09:00","Github reminder")
            }else{
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }

    }

    private fun saveReminder(b: Boolean) {
        val alarmPreference = IsRemindedPreference(this)
        isReminded = IsReminded()
        isReminded.isReminded =b
        alarmPreference.setReminder(isReminded)

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
                Intent(this@AlarmActivity, MainActivity::class.java).also {
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