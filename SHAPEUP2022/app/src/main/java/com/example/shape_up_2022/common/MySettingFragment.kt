package com.example.shape_up_2022.common

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import com.example.shape_up_2022.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MySettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MySettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        //val emailPreference: Preference?=findPreference("askemail")

    }
}