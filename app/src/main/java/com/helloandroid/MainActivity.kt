package com.helloandroid

import android.content.Context
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

const val PREFS_SIZE = "PREFS_SIZE"

class MainActivity : AppCompatActivity() {

    private lateinit var _todoList: MutableList<MutablePair<Boolean, String>>
    val todoList: MutableList<MutablePair<Boolean, String>>
        get() {
            return _todoList
        }
    lateinit var adapter: MyRecycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _todoList = loadList()
        if (_todoList.isEmpty()) {
            todoList += false to "One"
            todoList += true to "Two"
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecycleAdapter(todoList)
        recyclerView.adapter = adapter
        adapter.onChecked { pos, isChecked ->
            todoList[pos].first = isChecked
        }
        adapter.onTextChanged { pos, text ->
            todoList[pos].second = text
        }
    }

    override fun onPause() {
        super.onPause()
        saveList(_todoList)
    }

    fun loadList(): MutableList<MutablePair<Boolean, String>> {
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val size = prefs.getInt(PREFS_SIZE, 0)
        val list = mutableListOf<MutablePair<Boolean, String>>()
        for (i in 0 until size) {
            val isChecked = prefs.getBoolean("${i}b", false)
            val text = prefs.getString("${i}s", "")
            list.add(isChecked to text)
        }
        return list
    }

    fun saveList(list: MutableList<MutablePair<Boolean, String>>) {
        getPreferences(Context.MODE_PRIVATE).edit().apply {
            for (i in 0 until list.size) {
                putBoolean("${i}b", list[i].first)
                putString("${i}s", list[i].second)
            }
            putInt(PREFS_SIZE, list.size)
            apply()
        }
    }

    class MyViewHolder : RecyclerView.ViewHolder {
        val checkBox: CheckBox
        val text: EditText

        constructor(view: View) : super(view) {
            checkBox = view.checkBox
            text = view.editText
        }
    }

    class MyRecycleAdapter : RecyclerView.Adapter<MyViewHolder> {

        private val values: MutableList<MutablePair<Boolean, String>>
        private var onCheckedListener: (Int, Boolean) -> Unit = { a, b -> }
        private var onTextChangedListener: (Int, String) -> Unit = { a, b -> }

        constructor(list: MutableList<MutablePair<Boolean, String>>) {
            values = list
        }

        fun onChecked(action: (Int, Boolean) -> Unit) {
            onCheckedListener = action
        }

        fun onTextChanged(action: (Int, String) -> Unit) {
            onTextChangedListener = action
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

            holder.checkBox.setOnCheckedChangeListener { view, isChecked -> onCheckedListener(position, isChecked) }
            holder.text.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    onTextChangedListener(position, s.toString())
                }
            })
        }

        fun add(position: Int, item: String) {
            values.add(position, MutablePair(false, item))
            notifyItemInserted(position)
        }

        fun remove(position: Int) {
            values.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class MutablePair<A, B>(var first: A, var second: B)

    infix fun <A, B> A.to(that: B): MutablePair<A, B> {
        return MutablePair(this, that)
    }
}
