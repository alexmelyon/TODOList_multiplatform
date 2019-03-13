package com.helloandroid.world

import android.R
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.helloandroid.MainActivity
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.listView
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.sdk15.listeners.onItemClick
import org.jetbrains.anko.verticalLayout
import javax.inject.Inject

class WorldView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), WorldContract.View {

    @Inject
    lateinit var controller: WorldContract.Controller

    lateinit var worldsListView: ListView

    override fun createView(container: ViewGroup) = container.context.verticalLayout {
        worldsListView = listView {
            onItemClick { adapter, view, pos, id ->
                controller.onItemClick(pos)
            }
        }.lparams(matchParent, matchParent)
    }

    override fun setData(worlds: List<String>) {
        val context = worldsListView.context
        worldsListView.adapter = ArrayAdapter<String>(context, R.layout.simple_list_item_1, worlds)
    }
}