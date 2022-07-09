package com.example.shape_up_2022

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.shape_up_2022.databinding.FragmentWashing1Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WashingFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class SimWashingFragment1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding : FragmentWashing1Binding
    var status = 0
    var moveX = 0.0
    var moveY = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    // 액티비티 이동 리스너
    val activiy = object : DialogInterface.OnClickListener {
        override fun onClick(p0: DialogInterface?, p1: Int) {
            Log.d("mobileApp", "${p0}")
            p0?.dismiss()
            (activity as SimWashingToyActivity).replaceFragment(SimWashingFragment2())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWashing1Binding.inflate(inflater, container, false)

        // 특정 뷰에 들어가면 옮긴 이미지가 사라지도록
        binding.washToy1.setOnTouchListener(moving)
        binding.washToy2.setOnTouchListener(moving)
        binding.washToy3.setOnTouchListener(moving)
        binding.washToy4.setOnTouchListener(moving)
        binding.washToy5.setOnTouchListener(moving)

        return binding.root
    }

    var moving = View.OnTouchListener{ view, event ->
        when(event.action){
            MotionEvent.ACTION_MOVE -> {
                view.animate()
                    .x((event.rawX + moveX).toFloat())
                    .y((event.rawY + moveY).toFloat())
                    .setDuration(0)
                    .start()
            }

            MotionEvent.ACTION_DOWN -> {
                if (isbasin(view as ImageView) == true){
                    moveX = (view.x - event.rawX).toDouble()
                    moveY = (view.y - event.rawY).toDouble()
                    view.visibility = View.GONE
                    binding.washProgressBar.incrementProgressBy(1)

                    status++

                    if(status == 5){
                        //alert 창
                        AlertDialog.Builder(requireContext()).run {
                            Log.d("mobileApp", "창창!")
                            setTitle("따뜻한 물에 담그기 완료!")
                            setIcon(R.drawable.maltese)
                            setMessage("물에 20-30분간 담가둔 뒤, 물을 새로 받아서 베이킹 소다 적당량을 넣고 주물주물합니다.")
                            setPositiveButton("다음으로", activiy)
                            show()
                        }.setCanceledOnTouchOutside(false)
                }
            }
        }
        }
        true
    }
    private fun isbasin(image: ImageView): Boolean{
        return image.x > binding.basin.x && image.y >binding.basin.y && image.x < binding.basin.x+200 && image.y < binding.basin.y+200
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WashingFragment1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SimWashingFragment1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}