package com.helloandroid.session

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.helloandroid.R
import kotlinx.android.synthetic.main.session_item_comment.view.*
import kotlinx.android.synthetic.main.session_item_int.view.*
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.sdk15.listeners.onClick

class SessionDiffsAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = mutableListOf<SessionItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onItemPlus: (Int, SessionItemType) -> Unit = { pos, type -> }
    var onItemMinus: (Int, SessionItemType) -> Unit = { pos, type -> }
    var onCommentChanged: (Int, String) -> Unit = { pos, comment -> }

    private val textWatchers = mutableMapOf<EditText, IdTextWatcher>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val type = SessionItemType.values()[viewType]
        when (type) {
            SessionItemType.ITEM_HP,
            SessionItemType.ITEM_SKILL,
            SessionItemType.ITEM_THING -> {
                val view = parent.context.layoutInflater.inflate(R.layout.session_item_int, parent, false)
                return ItemIntViewHolder(view)
            }
            SessionItemType.ITEM_COMMENT -> {
                val view = parent.context.layoutInflater.inflate(R.layout.session_item_comment, parent, false)
                return ItemCommentViewHolder(view)
            }
        }
        throw NotImplementedError("Wrong viewType")
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = SessionItemType.values()[holder.itemViewType]
        when (type) {
            SessionItemType.ITEM_HP,
            SessionItemType.ITEM_SKILL,
            SessionItemType.ITEM_THING -> {
                holder as ItemIntViewHolder
                holder.title.text = items[position].title
                holder.desc.text = items[position].desc
                holder.value.text = items[position].value.toString()
                holder.minusButton.onClick { view ->
                    val correctPosition = holder.adapterPosition
                    onItemMinus(items[correctPosition].index, type)
                }
                holder.plusButton.onClick { view ->
                    val correctPosition = holder.adapterPosition
                    onItemPlus(items[correctPosition].index, type)
                }
            }
            SessionItemType.ITEM_COMMENT -> {
                holder as ItemCommentViewHolder
                holder.editText.setText(items[position].comment, TextView.BufferType.EDITABLE)
                if (textWatchers[holder.editText] == null) {
                    val watcher = IdTextWatcher() { index, comment ->
                        val correctPosition = holder.adapterPosition
//                        onCommentChanged(index, comment)
                        onCommentChanged(correctPosition, comment)
                    }
                    textWatchers[holder.editText] = watcher
                    holder.editText.addTextChangedListener(watcher)
                }
                textWatchers[holder.editText]!!.index = position
            }
        }
    }

    class IdTextWatcher(val textChanged: (Int, String) -> Unit) : TextWatcher {
        var index: Int = 0

        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            textChanged(index, s.toString())
        }
    }

    class ItemIntViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.title
        val desc = view.desc
        val value = view.value
        val minusButton = view.minusButton
        val plusButton = view.plusButton
    }

    class ItemCommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val editText = view.editText
    }
}