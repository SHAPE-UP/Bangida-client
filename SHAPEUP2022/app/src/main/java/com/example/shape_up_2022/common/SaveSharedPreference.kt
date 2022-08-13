package com.example.shape_up_2022.common

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

public class SaveSharedPreference {
    companion object{
        fun getSharedPreferences(ctx: Context): SharedPreferences? {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        // User의 email
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

        // User의 name
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

        // User의 _id
        fun setUserID(ctx: Context, name: String) {
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("userID", name)
            editor.commit()
        }
        fun getUserID(ctx: Context?): String? { return getSharedPreferences(ctx!!)!!.getString("userID", "") }
        fun clearUserID(ctx: Context?) {
            val editor = getSharedPreferences(ctx!!)!!.edit()
            editor.clear()
            editor.commit()
        }

        // Family의 _id
        fun setFamliyID(ctx: Context, name: String) {
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("familyID", name)
            editor.commit()
        }
        fun getFamliyID(ctx: Context?): String? { return getSharedPreferences(ctx!!)!!.getString("familyID", "") }
        fun clearFamliyID(ctx: Context?) {
            val editor = getSharedPreferences(ctx!!)!!.edit()
            editor.clear()
            editor.commit()
        }

    }
}