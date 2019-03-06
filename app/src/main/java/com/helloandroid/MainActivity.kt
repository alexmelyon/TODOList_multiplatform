package com.helloandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.view.*

class MainActivity : AppCompatActivity() {

    val todoList = mutableListOf<Pair<Boolean, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoList += false to "One"
        todoList += true to "Two"

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyRecycleAdapter(todoList)
    }

    class MyViewHolder : RecyclerView.ViewHolder {
        val checkBox: CheckBox
        val text: EditText
        constructor(view: View) :super(view) {
            checkBox = view.checkBox
            text = view.editText
        }
    }

    class MyRecycleAdapter : RecyclerView.Adapter<MyViewHolder> {

        private val values: MutableList<Pair<Boolean, String>>

        constructor(list: MutableList<Pair<Boolean, String>>) {
            values = list
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val v = inflater.inflate(R.layout.list_item, parent, false)
            val viewHolder = MyViewHolder(v)
            return viewHolder
        }

        override fun getItemCount(): Int {
            return values.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val text = values[position].second
            holder.text.setText(text, TextView.BufferType.EDITABLE)
            holder.checkBox.isChecked = values[position].first
            holder.text.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }

        fun add(position: Int, item: String) {
            values.add(position, Pair(false, item))
            notifyItemInserted(position)
        }

        fun remove(position: Int) {
            values.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
