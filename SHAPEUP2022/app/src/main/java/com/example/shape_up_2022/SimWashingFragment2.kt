package com.example.shape_up_2022

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.shape_up_2022.databinding.FragmentWashing2Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WashingFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class SimWashingFragment2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding : FragmentWashing2Binding
    var status = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    val activiy = object : DialogInterface.OnClickListener {
        override fun onClick(p0: DialogInterface?, p1: Int) {
            val intent = Intent(requireContext(), SimToyPuppyActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWashing2Binding.inflate(inflater, container, false)

        binding.washingToys.setOnLongClickListener {
            status++
            binding.washingProgressBar2.incrementProgressBy(1)

            if (status == 10) {
                AlertDialog.Builder(requireContext()).run {
                    setTitle("장난감 세척 완료!")
                    setIcon(R.drawable.puppy)
                    setMessage("이제 곰팡이, 세균이 생기지 않도록 햇볕에 말리거나 건조기로 건조하면 끝~!")
                    setPositiveButton("완료!", activiy)
                    show()
                }.setCanceledOnTouchOutside(true)
            }

            true
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
         * @return A new instance of fragment WashingFragment2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SimWashingFragment2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}