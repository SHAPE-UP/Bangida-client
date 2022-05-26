package com.example.shape_up_2022

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.databinding.FragmentJoinCreateBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JoinCreate.newInstance] factory method to
 * create an instance of this fragment.
 */
class JoinCreate : Fragment(){

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

        // 버튼 view 바인딩
        val binding = FragmentJoinCreateBinding.inflate(inflater,  container, false)
        binding.nextCreate.setOnClickListener{
            (activity as JoinActivity).replaceFragment(JoinShare())
        }

        binding.nextCreate.isEnabled = false
        binding.nextCreate.setBackgroundColor(Color.parseColor("#d3d3d3"));

        // EditText binding
        val input_name = binding.inputName
        val input_id = binding.newId
        val new_password = binding.inputNewpassword
        val check_password = binding.checkPassword


        // 핸들러 적용
        var textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (input_name.length() > 0 && input_id.length() > 0 && new_password.length() > 0 && check_password.length() > 0){
                    binding.nextCreate.isEnabled = true
                    binding.nextCreate.setBackgroundColor(Color.parseColor("#FF9966"));
                } else{
                    binding.nextCreate.isEnabled = false
                    binding.nextCreate.setBackgroundColor(Color.parseColor("#d3d3d3"));
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }

        // 이벤트 핸들러 연결하기
        input_name.addTextChangedListener(textWatcher)
        input_id.addTextChangedListener(textWatcher)
        new_password.addTextChangedListener(textWatcher)
        check_password.addTextChangedListener(textWatcher)

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JoinCreate.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JoinCreate().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}