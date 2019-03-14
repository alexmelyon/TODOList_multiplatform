package com.helloandroid.list_sessions

import android.view.ViewGroup
import com.helloandroid.MainActivity
import com.helloandroid.ui.RecyclerStringAdapter
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import javax.inject.Inject

class ListSessionsView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), ListSessionsContract.View {

    @Inject
    lateinit var controller: ListSessionsContract.Controller

    lateinit var sessionsAdapter: RecyclerStringAdapter

    override fun createView(container: ViewGroup) = container.context.recyclerView {
        activity.supportActionBar!!.title = controller.getGameName()

        sessionsAdapter = RecyclerStringAdapter(container.context)
        adapter = sessionsAdapter
    }

    override fun setData(items: List<String>) {
        sessionsAdapter.items = items
    }

}