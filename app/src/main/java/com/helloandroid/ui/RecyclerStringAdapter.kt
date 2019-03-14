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

class RecyclerStringAdapter(val context: Context, val onItemClickListener: (Int) -> Unit = { pos -> }) : RecyclerView.Adapter<RecyclerStringAdapter.ViewHolder>() {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    var items: List<String> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        v.findViewById<TextView>(android.R.id.text1).textColor = Color.BLACK
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(android.R.id.text1).text = items[position]
        holder.itemView.findViewById<TextView>(android.R.id.text1).setOnClickListener { view ->
            onItemClickListener(position)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}