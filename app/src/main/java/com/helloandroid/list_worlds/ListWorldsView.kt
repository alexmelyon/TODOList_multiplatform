package com.helloandroid.list_worlds

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.helloandroid.MainActivity
import com.helloandroid.R
import com.helloandroid.ui.RecyclerStringAdapter
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.verticalLayout
import javax.inject.Inject

class ListWorldsView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), ListWorldsContract.View {

    @Inject
    lateinit var controller: ListWorldsContract.Controller

    lateinit var worldsView: RecyclerView
    lateinit var worldsAdapter: RecyclerStringAdapter

    override fun createView(container: ViewGroup) = container.context.verticalLayout {
        activity.supportActionBar!!.title = container.context.getString(R.string.app_name)
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