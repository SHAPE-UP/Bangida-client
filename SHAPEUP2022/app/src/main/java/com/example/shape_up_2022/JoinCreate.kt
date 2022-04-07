package com.example.shape_upapptest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.shape_upapptest.databinding.AccountJoinBinding
import com.example.shape_upapptest.databinding.FragmentJoinCreateBinding
import com.example.shape_upapptest.databinding.FragmentJoinShareBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JoinCreate.newInstance] factory method to
 * create an instance of this fragment.
 */
class JoinCreate : Fragment(), View.OnClickListener {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val binding by lazy { FragmentJoinCreateBinding.inflate(layoutInflater)}

    //var ButtonInFragment: Button? = null

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

        val view: View = inflater.inflate(R.layout.fragment_join_create, null) // Fragment로 불러올 xml파일을 view로 가져옵니다.
        val button1 = view.findViewById<View>(R.id.next_create) as Button // click시 Fragment를 전환할 event를 발생시킬 버튼을 정의합니다.
        button1.setOnClickListener(this)

        return view

        // Inflate the layout for this fragment
        //val rootView = inflater.inflate(R.layout.fragment_join_create, container, false)

        //binding.nextCreate.setOnClickListener(this)

        //return rootView
        //return inflater.inflate(R.layout.fragment_join_create, container, false)
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
    override fun onClick(p0: View?) {
        Log.d("app_test", "온클릭 리스너 이벤트")
        (activity as JoinActivity).replaceFragment(JoinShare())
    }


}