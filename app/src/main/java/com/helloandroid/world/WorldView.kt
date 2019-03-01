package com.helloandroid.world

import android.R
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import org.jetbrains.anko.listView
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.sdk15.listeners.onItemClick
import org.jetbrains.anko.verticalLayout
import javax.inject.Inject

class WorldView : WorldContract.View {

    private lateinit var controller: WorldContract.Controller

    @Inject
    override fun setController(controller: WorldContract.Controller) {
        this.controller = controller
    }

    lateinit var worldsListView: ListView

    override fun createView(container: ViewGroup): View {
        return container.context.verticalLayout {
            worldsListView = listView {
                onItemClick { adapter, view, pos, id ->
                    controller.onItemClick(pos)
                    Log.i("JCD", "ON CLICK $pos, ${adapter?.getItemAtPosition(pos)}")
                }
            }.lparams(matchParent, matchParent)
        }
    }



    override fun setData(worlds: List<String>) {
        val context = worldsListView.context
        worldsListView.adapter = ArrayAdapter<String>(context, R.layout.simple_list_item_1, worlds)
    }
}