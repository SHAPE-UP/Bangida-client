package com.example.shape_up_2022.common

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shape_up_2022.R
import com.example.shape_up_2022.adapter.TodoViewHolder
import com.example.shape_up_2022.data.TodoItem
import com.example.shape_up_2022.data.User
import com.example.shape_up_2022.databinding.FragmentFamilyBinding
import com.example.shape_up_2022.databinding.ItemFamilyBinding
import com.example.shape_up_2022.retrofit.GetFamilyReq
import com.example.shape_up_2022.retrofit.GetFamilyRes
import com.example.shape_up_2022.retrofit.MyApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/*
class FamilyAdapter(private val items: MutableList<User>): BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): User = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var convertView = view ?: LayoutInflater.from(parent?.context).inflate(R.layout.item_family, parent, false)

        val item: User = items[position]
        //convertView!!.f_image.setImageDrawable(item.icon)
        //convertView.f_name.text = item.name

        val iv = convertView.findViewById<ImageView>(R.id.f_image)
        val tv = convertView.findViewById<TextView>(R.id.f_name)

        //iv.setImageDrawable(item.image)
        tv.text = item.name

        return convertView
    }
}
*/
class FamilyViewHolder(val binding: ItemFamilyBinding): RecyclerView.ViewHolder(binding.root)
class FamilyAdapter (val datas: MutableList<User>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FamilyViewHolder(
            ItemFamilyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as FamilyViewHolder).binding

        //binding.fImage.setImageDrawable(datas!![position].image)
        binding.fName.text = datas!![position].name

    }
}


/**
 * A simple [Fragment] subclass.
 * Use the [FamilyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FamilyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var familyArray = mutableListOf<User>()  // 가족 구성원 목록
    lateinit var familyAdapter: FamilyAdapter

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
        // Inflate the layout for this fragment
        val binding = FragmentFamilyBinding.inflate(inflater, container, false)

        // 리사이클러뷰 설정 및 초기화
        val layoutManager = LinearLayoutManager(context)
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL)
        binding.familyListView.layoutManager = layoutManager
        familyAdapter = FamilyAdapter(familyArray)
        binding.familyListView.adapter = familyAdapter

        val call: Call<GetFamilyRes> = MyApplication.networkServiceFamily.getFamily(
            GetFamilyReq(SaveSharedPreference.getFamliyID(context)!!)
        )
        call?.enqueue(object : Callback<GetFamilyRes> {
            override fun onResponse(call: Call<GetFamilyRes>, response: Response<GetFamilyRes>) {
                if(response.isSuccessful){
                    //Log.d("mobileApp", "$response ${response.body()}")
                    familyArray = response.body()!!.family.userGroup.toMutableList()
                    familyAdapter = FamilyAdapter(familyArray)
                    binding.familyListView.adapter = familyAdapter
                    familyAdapter.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<GetFamilyRes>, t: Throwable) {
                Log.d("mobileApp", "getFamily onFailure $t")
            }
        })



        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FamilyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FamilyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}