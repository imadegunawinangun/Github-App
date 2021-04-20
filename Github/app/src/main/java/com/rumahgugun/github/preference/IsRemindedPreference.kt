package com.rumahgugun.github.preference

import android.content.Context
import com.rumahgugun.github.other.IsReminded

class IsRemindedPreference(context: Context) {
    companion object{
        const val PREFS_NAME = "alarm_pref"
        private const val REMINDER = "isRemind"
    }

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setReminder(isReminded: IsReminded){
        val editor = preference.edit()
        editor.putBoolean(REMINDER, isReminded.isReminded)
        editor.apply()
    }

    fun getReminder():IsReminded{
        val model = IsReminded()
        model.isReminded = preference.getBoolean(REMINDER, false)
        return model
    }

}
