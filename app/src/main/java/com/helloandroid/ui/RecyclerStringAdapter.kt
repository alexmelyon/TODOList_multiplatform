package com.helloandroid.ui

import android.content.Context
import android.graphics.Color
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.helloandroid.R
import org.jetbrains.anko.textColor

class RecyclerStringAdapter<T>(val context: Context, @LayoutRes val layoutRes: Int = android.R.layout.simple_list_item_1, val onItemClickListener: (Int, T) -> Unit = { pos, item -> }) : RecyclerView.Adapter<RecyclerStringAdapter.ViewHolder>() {

    var layoutManager: RecyclerView.LayoutManager? = null
    var onItemLongclickListener: (Int, T) -> Unit = { pos, item -> }
    var onGetDescriptionValue: ((Int) -> String)? = null
    var onGetHeaderValue: ((Int) -> String)? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
    }

    var items: MutableList<T> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(layoutRes, parent, false)
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
            onItemClickListener(correctPosition, items[position])
        }
        holder.itemView.setOnLongClickListener { view ->
            onItemLongclickListener(correctPosition, items[position])
            return@setOnLongClickListener true
        }
        if (onGetDescriptionValue != null) {
            holder.text2.text = onGetDescriptionValue?.invoke(correctPosition)
        }
        if(onGetHeaderValue != null) {
            val header = onGetHeaderValue!!.invoke(correctPosition)
            if(header.isNotEmpty()) {
                holder.headerView?.visibility = View.VISIBLE
                holder.headerText?.text = header
            } else {
                holder.headerView?.visibility = View.GONE
                holder.headerText?.text = ""
            }
        }
    }

    fun itemAddedAt(pos: Int, name: T) {
        items.add(pos, name)
        notifyItemInserted(pos)

        layoutManager?.scrollToPosition(0)
    }

    fun itemRemovedAt(pos: Int) {
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)
        val headerView = view.findViewById<FrameLayout>(R.id.header_view)
        val headerText = view.findViewById<TextView>(R.id.header_text)
    }
}