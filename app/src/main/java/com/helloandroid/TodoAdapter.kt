package com.helloandroid

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.alexmelyon.todocore.TodoItem
import kotlinx.android.synthetic.main.todo_add.view.*
import kotlinx.android.synthetic.main.todo_item.view.*


class TodoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TODO_ITEM = 0
    val TODO_ADD = 1
    var values: MutableList<TodoItem> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var onCheckedListener: (Int, Boolean) -> Unit = { a, b -> }
    private var onTextChangedListener: (Int, String) -> Unit = { a, b -> }
    private var onAddItemListener: () -> Unit = { }

    fun onChecked(action: (Int, Boolean) -> Unit) {
        onCheckedListener = action
    }

    fun onTextChanged(action: (Int, String) -> Unit) {
        onTextChangedListener = action
    }

    fun onAddItem(action: () -> Unit) {
        onAddItemListener = action
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
        when (holder.itemViewType) {
            TODO_ITEM -> {
                holder as TodoItemViewHolder
                val text = values[position].text
                holder.text.setText(text, TextView.BufferType.EDITABLE)
                holder.checkBox.isChecked = values[position].checked

                holder.checkBox.setOnCheckedChangeListener { view, isChecked ->
                    onCheckedListener(position, isChecked)
                }
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
                holder.button.setOnClickListener { onAddItemListener() }
            }
        }
    }

    fun added() {
        notifyItemInserted(values.size - 1)
    }

    fun removedAt(position: Int) {
        notifyItemRemoved(position)
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