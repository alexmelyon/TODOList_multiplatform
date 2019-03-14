package com.helloandroid.world

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.helloandroid.MainActivity
import com.helloandroid.ui.RecyclerStringAdapter
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.verticalLayout
import javax.inject.Inject

class WorldView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), WorldContract.View {

    @Inject
    lateinit var controller: WorldContract.Controller

    lateinit var worldsView: RecyclerView
    lateinit var worldsAdapter: RecyclerStringAdapter

    override fun createView(container: ViewGroup) = container.context.verticalLayout {
        worldsAdapter = RecyclerStringAdapter(container.context) { pos ->
            controller.onItemClick(pos)
        }
        worldsView = recyclerView {
            adapter = worldsAdapter
        }.lparams(matchParent, matchParent)
    }

    override fun setData(items: List<String>) {
        worldsAdapter.items = items
    }
}