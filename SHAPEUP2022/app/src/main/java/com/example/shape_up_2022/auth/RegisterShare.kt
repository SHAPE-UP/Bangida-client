package com.example.shape_up_2022.auth

import com.example.shape_up_2022.common.SaveSharedPreference
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.databinding.FragmentJoinShareBinding
import com.example.shape_up_2022.retrofit.JoinFamilyReq
import com.example.shape_up_2022.retrofit.JoinFamilyRes
import com.example.shape_up_2022.retrofit.MyApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JoinShare.newInstance] factory method to
 * create an instance of this fragment.
 */
class JoinShare : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 반려견 공유 코드를 입력하면 다음으로
        val binding = FragmentJoinShareBinding.inflate(inflater, container, false)
        binding.nextShare.setOnClickListener {

            val familyCode: String = binding.familyCode.text.toString()
            val userID: String = SaveSharedPreference.getUserID(requireActivity().applicationContext!!)!!

            if(familyCode == "") {  // 입력한 코드가 없으면
                //(activity as RegisterActivity).gotoMainActivity()  // 이거 말고 로그인 액티비티로
            }

            else {
                val call: Call<JoinFamilyRes> = MyApplication.networkServiceAuth.joinFamliy(
                    JoinFamilyReq(familyCode = familyCode, userID = userID)
                )

                call?.enqueue(object : Callback<JoinFamilyRes> {
                    override fun onResponse(call: Call<JoinFamilyRes>, response: Response<JoinFamilyRes>) {
                        if(response.isSuccessful){
                            Log.d("mobileApp", "$response ${response.body()}")

                            /*
                            //////작업중
                            if (리스폰스.success == true) {
                                // 로그인 액티비티로 이동
                            } else {  // success == false
                                Toast.makeText(requireContext(), "올바르지 않은 코드입니다.", Toast.LENGTH_LONG)
                            }
                            */

                        }
                    }

                    override fun onFailure(call: Call<JoinFamilyRes>, t: Throwable) {
                        Log.d("mobileApp", "onFailure $t")
                    }
                })
            }
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JoinShare.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JoinShare().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}