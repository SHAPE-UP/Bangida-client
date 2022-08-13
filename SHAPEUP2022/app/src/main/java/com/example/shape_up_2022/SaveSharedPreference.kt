package com.example.shape_up_2022

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SaveSharedPreference {
    companion object{
        fun getSharedPreferences(ctx: Context): SharedPreferences? {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        // email
        fun setUserEmail(ctx: Context, email: String) {
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("useremail", email)
            editor.commit()
        }
        fun getUserEmail(ctx: Context?): String? { return getSharedPreferences(ctx!!)!!.getString("useremail", "") }
        fun clearUserEmail(ctx: Context?) {
            val editor = getSharedPreferences(ctx!!)!!.edit()
            editor.clear()
            editor.commit()
        }

        // 닉네임
        fun setUserName(ctx: Context, name: String) {
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("username", name)
            editor.commit()
        }
        fun getUserName(ctx: Context?): String? { return getSharedPreferences(ctx!!)!!.getString("username", "") }
        fun clearUserName(ctx: Context?) {
            val editor = getSharedPreferences(ctx!!)!!.edit()
            editor.clear()
            editor.commit()
        }

    }
}