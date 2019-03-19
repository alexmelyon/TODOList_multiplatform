package com.helloandroid.list_games

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.helloandroid.MainActivity
import com.helloandroid.ui.RecyclerStringAdapter
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import javax.inject.Inject

class ListGamesView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), ListGamesContract.View {

    @Inject
    lateinit var controller: ListGamesContract.Controller

    lateinit var gamesView: RecyclerView
    lateinit var gamesAdapter: RecyclerStringAdapter

    override fun createView(container: ViewGroup) = container.context.linearLayout {
        activity.supportActionBar!!.title = controller.getWorldName()

        gamesAdapter = RecyclerStringAdapter(container.context) { pos ->
            controller.onItemClick(pos)
        }
        gamesView = recyclerView {
            adapter = gamesAdapter
        }
    }

    override fun setData(items: MutableList<String>) {
        gamesAdapter.items = items
    }

}