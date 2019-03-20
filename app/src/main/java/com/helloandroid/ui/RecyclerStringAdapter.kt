package com.helloandroid.ui

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.textColor

class RecyclerStringAdapter<T>(val context: Context, val onItemClickListener: (Int) -> Unit = { pos -> }) : RecyclerView.Adapter<RecyclerStringAdapter.ViewHolder>() {

    var onItemLongclickListener: (Int, T) -> Unit = { pos, item -> }
    var onDescriptionValue: ((Int) -> String)? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    var items: MutableList<T> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = if(onDescriptionValue == null) {
            android.R.layout.simple_list_item_1
        } else {
            android.R.layout.simple_list_item_2
        }
        val v = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)
        val vh = ViewHolder(v)
        vh.text1.textColor = Color.BLACK
        vh.text2?.textColor = Color.GRAY
        return vh
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val correctPosition = holder.adapterPosition
        val text1 = holder.text1
        text1.text = items[position].toString()
        holder.itemView.setOnClickListener { view ->
            onItemClickListener(correctPosition)
        }
        holder.itemView.setOnLongClickListener { view ->
            onItemLongclickListener(correctPosition, items[position])
            return@setOnLongClickListener true
        }
        if(onDescriptionValue != null) {
            holder.text2.text = onDescriptionValue?.invoke(correctPosition)
        }
    }

    fun itemAddedAt(pos: Int, name: T) {
        items.add(pos, name)
        notifyItemInserted(pos)
    }

    fun itemRemovedAt(pos: Int) {
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)
    }
}