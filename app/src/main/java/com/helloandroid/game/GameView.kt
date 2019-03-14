package com.helloandroid.game

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.helloandroid.MainActivity
import com.helloandroid.ui.RecyclerStringAdapter
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import javax.inject.Inject

class GameView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), GameContract.View {

    @Inject
    lateinit var controller: GameContract.Controller

    lateinit var gamesView: RecyclerView
    lateinit var gamesAdapter: RecyclerStringAdapter

    override fun createView(container: ViewGroup) = container.context.linearLayout {
        gamesAdapter = RecyclerStringAdapter(container.context) { pos ->
            controller.onItemClick(pos)
        }
        gamesView = recyclerView {
            adapter = gamesAdapter
        }
    }

    override fun setData(items: List<String>) {
        gamesAdapter.items = items
    }

}