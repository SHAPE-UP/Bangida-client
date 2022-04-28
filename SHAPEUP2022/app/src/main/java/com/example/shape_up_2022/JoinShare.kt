<<<<<<< HEAD
package com.example.shape_up_2022

import android.os.Bundle
=======
package com.example.shape_upapptest

import android.content.Intent
import android.os.Bundle
import android.util.Log
>>>>>>> e162eb89f48732050dca3ebbd7b84501d5ec16c4
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import com.example.shape_up_2022.databinding.FragmentJoinShareBinding

=======
import android.widget.Button
import com.example.shape_upapptest.databinding.FragmentJoinCreateBinding
import com.example.shape_upapptest.databinding.FragmentJoinShareBinding
>>>>>>> e162eb89f48732050dca3ebbd7b84501d5ec16c4

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JoinShare.newInstance] factory method to
 * create an instance of this fragment.
 */
<<<<<<< HEAD
class JoinShare : Fragment() {
=======
class JoinShare : Fragment(), View.OnClickListener {
>>>>>>> e162eb89f48732050dca3ebbd7b84501d5ec16c4
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
<<<<<<< HEAD
        val binding = FragmentJoinShareBinding.inflate(inflater, container, false)
        binding.nextShare.setOnClickListener {
            (activity as JoinActivity).gotoMainActivity()
        }

        return binding.root
=======
        val view: View = inflater.inflate(R.layout.fragment_join_share, null)
        val button1 = view.findViewById<View>(R.id.next_share) as Button
        button1.setOnClickListener(this)

        return view
>>>>>>> e162eb89f48732050dca3ebbd7b84501d5ec16c4
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

<<<<<<< HEAD
=======
    override fun onClick(p0: View?) {
        (activity as JoinActivity).gotoMainActivity()
    }

>>>>>>> e162eb89f48732050dca3ebbd7b84501d5ec16c4

}