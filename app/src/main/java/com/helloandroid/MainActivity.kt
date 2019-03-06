package com.helloandroid

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todo_add.view.*
import kotlinx.android.synthetic.main.todo_item.view.*

const val PREFS_SIZE = "PREFS_SIZE"

class MainActivity : AppCompatActivity() {

    private lateinit var _todoList: MutableList<MutablePair<Boolean, String>>
    val todoList: MutableList<MutablePair<Boolean, String>>
        get() {
            return _todoList
        }
    lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _todoList = loadList()
        if (_todoList.isEmpty()) {
            todoList += false to "One"
            todoList += true to "Two"
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodoAdapter(todoList)
        recyclerView.adapter = adapter
        adapter.onChecked { pos, isChecked ->
            todoList[pos].first = isChecked
        }
        adapter.onTextChanged { pos, text ->
            todoList[pos].second = text
        }
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                adapter.remove(viewHolder.adapterPosition)
//                todoList.removeAt(viewHolder.adapterPosition)
//                adapter.notifyItemChanged(viewHolder.adapterPosition)
            }
        }.let { ItemTouchHelper(it).attachToRecyclerView(recyclerView) }
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

    class TodoItemViewHolder : RecyclerView.ViewHolder {
        val checkBox: CheckBox
        val text: EditText

        constructor(view: View) : super(view) {
            checkBox = view.checkBox
            text = view.editText
        }
    }

    class TodoAddViewHolder : RecyclerView.ViewHolder {
        val button: Button

        constructor(view: View) : super(view) {
            button = view.button
        }
    }

    class TodoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

        val TODO_ITEM = 0
        val TODO_ADD = 1
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

        override fun getItemViewType(position: Int): Int {
            if (position < values.size) {
                return 0
            }
            return 1
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            when (viewType) {
                TODO_ITEM -> {
                    val v = inflater.inflate(R.layout.todo_item, parent, false)
                    val viewHolder = TodoItemViewHolder(v)
                    return viewHolder
                }
                TODO_ADD -> {
                    val v = inflater.inflate(R.layout.todo_add, parent, false)
                    val viewHolder = TodoAddViewHolder(v)
                    return viewHolder
                }
            }
            throw IllegalStateException("Wrong ViewHolder")
        }

        override fun getItemCount(): Int {
            return values.size + 1
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when(holder.itemViewType) {
                TODO_ITEM -> {
                    holder as TodoItemViewHolder
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
                TODO_ADD -> {
                    holder as TodoAddViewHolder
                    holder.button.setOnClickListener { add() }
                }
            }
        }

        fun add() {
            values.add(MutablePair(false, ""))
            notifyItemInserted(values.size - 1)
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
