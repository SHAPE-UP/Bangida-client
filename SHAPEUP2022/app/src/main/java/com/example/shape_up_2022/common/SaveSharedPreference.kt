package com.example.shape_up_2022.common

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SaveSharedPreference {
    companion object{
        fun getSharedPreferences(ctx: Context): SharedPreferences? {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        // User의 tested
        fun setUserTested(ctx: Context, tested: Boolean) {
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putBoolean("tested", tested)
            editor.commit()
        }
        fun getUserTested(ctx: Context?): Boolean? { return getSharedPreferences(ctx!!)!!.getBoolean("tested", false) }

        // User의 email
        fun setUserEmail(ctx: Context, email: String) {
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("useremail", email)
            editor.commit()
        }
        fun getUserEmail(ctx: Context?): String? { return getSharedPreferences(ctx!!)!!.getString("useremail", "") }

        // User의 name
        fun setUserName(ctx: Context, name: String) {
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("username", name)
            editor.commit()
        }
        fun getUserName(ctx: Context?): String? { return getSharedPreferences(ctx!!)!!.getString("username", "") }
        /*
        fun clearUserName(ctx: Context?) {
            val editor = getSharedPreferences(ctx!!)!!.edit()
            editor.remove("username")  // 특정 키-값만 삭제
            editor.commit()
        }
        */

        // User의 _id
        fun setUserID(ctx: Context, name: String) {
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("userID", name)
            editor.commit()
        }
        fun getUserID(ctx: Context?): String? { return getSharedPreferences(ctx!!)!!.getString("userID", "") }

        // Family의 _id
        fun setFamliyID(ctx: Context, familyID: String?) {
            if (familyID==null) return
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("familyID", familyID)
            editor.commit()
        }
        fun getFamliyID(ctx: Context?): String? { return getSharedPreferences(ctx!!)!!.getString("familyID", "") }

        // Pet의 _id
        fun setPetID(ctx: Context, petID: String?) {
            if (petID==null) return
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("petID", petID)
            editor.commit()
        }
        fun getPetID(ctx: Context?): String? { return getSharedPreferences(ctx!!)!!.getString("petID", "") }

        // User의 achieve
        fun setAchieve(ctx: Context, achieve: ArrayList<Boolean>?) {
            if (achieve==null) return
            val gson = Gson()
            val json = gson.toJson(achieve)
            val editor = getSharedPreferences(ctx)!!.edit()
            editor.putString("achieve", json)
            editor.commit()
        }
        fun getAchieve(ctx: Context?): ArrayList<Boolean>? {
            val json = getSharedPreferences(ctx!!)!!.getString("achieve", "")
            val gson = Gson()
            return gson.fromJson(
                json,
                object : TypeToken<ArrayList<Boolean?>>() {}.type
            )
        }

        // 저장된 SharedPreference 값 전체 삭제
        fun clearAll(ctx: Context?) {
            val editor = getSharedPreferences(ctx!!)!!.edit()
            editor.clear()
            editor.commit()
        }

    }
}