package com.helloandroid.world

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.helloandroid.MainActivity
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.textColor
import org.jetbrains.anko.verticalLayout
import javax.inject.Inject

class WorldView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), WorldContract.View {

    @Inject
    lateinit var controller: WorldContract.Controller

    lateinit var worldsListView: RecyclerView

    override fun createView(container: ViewGroup) = container.context.verticalLayout {
        worldsListView = recyclerView {
            layoutManager = LinearLayoutManager(container.context)
            adapter = RecyclerStringAdapter()
            (adapter as RecyclerStringAdapter).onItemClickListener = { pos ->
                controller.onItemClick(pos)
            }
        }.lparams(matchParent, matchParent)
    }

    override fun setData(worlds: List<String>) {
        val context = worldsListView.context
//        worldsListView.adapter = ArrayAdapter<String>(context, R.layout.simple_list_item_1, worlds)

        (worldsListView.adapter as RecyclerStringAdapter).items = worlds
    }

    class RecyclerStringAdapter : RecyclerView.Adapter<RecyclerStringAdapter.ViewHolder>() {

        var items: List<String> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }
        var onItemClickListener: (Int) -> Unit = { pos -> }

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
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    }
}