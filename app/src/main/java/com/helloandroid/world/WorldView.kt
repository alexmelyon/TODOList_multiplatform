package com.helloandroid.world

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.helloandroid.MainActivity
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.verticalLayout
import javax.inject.Inject

class WorldView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), WorldContract.View {

    @Inject
    lateinit var controller: WorldContract.Controller

    lateinit var worldsListView: RecyclerView
    lateinit var worldsAdapter: RecyclerStringAdapter

    override fun createView(container: ViewGroup) = container.context.verticalLayout {
        worldsAdapter = RecyclerStringAdapter(container.context) { pos ->
//            controller.onItemClick(pos)
            Log.i("JCD", "ON ITEM CLICK $pos")
        }
        worldsListView = recyclerView {
            adapter = worldsAdapter
        }.lparams(matchParent, matchParent)
    }

    override fun setData(items: List<String>) {
        (worldsListView.adapter as RecyclerStringAdapter).items = items
    }
}