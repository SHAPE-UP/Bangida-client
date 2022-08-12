package com.example.shape_up_2022.common

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SaveSharedPreference {
    companion object{
        fun getSharedPreferences(ctx: Context): SharedPreferences? {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        fun setUserEmail(ctx: Context, email: String) {
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("username", email)
            editor.commit()
        }

        fun getUserEmail(ctx: Context?): String? {
            return getSharedPreferences(ctx!!)!!.getString("username", "")
        }

        fun clearUserEmail(ctx: Context?) {
            val editor = getSharedPreferences(ctx!!)!!.edit()
            editor.clear()
            editor.commit()
        }
    }
}