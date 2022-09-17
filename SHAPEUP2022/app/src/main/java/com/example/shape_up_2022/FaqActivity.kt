package com.example.shape_up_2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shape_up_2022.adapter.ExpandableAdapter
import com.example.shape_up_2022.data.Person

class FaqActivity : AppCompatActivity() {

    private lateinit var personList: List<Person>
    private lateinit var adapter: ExpandableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_list)

        personList = loadData()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpandableAdapter(personList)
        recyclerView.adapter = adapter
    }

    private fun loadData(): List<Person> {
        val people = ArrayList<Person>()

        val persons = resources.getStringArray(R.array.people)
        val exps = resources.getStringArray(R.array.exp)

        for (i in persons.indices) {
            val person = Person().apply {
                name = persons[i]
                exp = exps[i]
            }
            people.add(person)
        }
        return people
    }
}