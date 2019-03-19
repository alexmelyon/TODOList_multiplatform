package com.helloandroid.list_characters

import android.view.ViewGroup
import java.util.*

interface ListCharactersContract {

    interface View {
        fun createView(container: ViewGroup): android.view.View
        fun setData(items: MutableList<CharacterItem>)
    }

    interface Controller {

    }
}

class CharacterItem(val name: String, val hp: Int, val skills: List<Pair<String, Int>>, val things: List<Pair<String, Int>>)
