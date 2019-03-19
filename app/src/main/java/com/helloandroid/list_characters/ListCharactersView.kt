package com.helloandroid.list_characters

import android.view.View
import android.view.ViewGroup
import com.helloandroid.MainActivity
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import javax.inject.Inject

class ListCharactersView @Inject constructor(activity: MainActivity) : _FrameLayout(activity), ListCharactersContract.View {

    @Inject
    lateinit var controller: ListCharactersContract.Controller

    val charactersAdapter = CharactersAdapter(activity)

    override fun createView(container: ViewGroup): View {
        return container.context.recyclerView {
            adapter = charactersAdapter
        }
    }

    override fun setData(items: MutableList<CharacterItem>) {
        charactersAdapter.items = items
    }
}