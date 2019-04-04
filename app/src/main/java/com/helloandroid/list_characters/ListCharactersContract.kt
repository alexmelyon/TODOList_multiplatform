package com.helloandroid.list_characters

import android.view.ViewGroup
import java.util.*

interface ListCharactersContract {

    interface View {
        fun createView(container: ViewGroup): android.view.View
        fun setData(items: MutableList<CharacterItem>)
        fun showAddCharacterDialog()
        fun addedAt(index: Int, item: CharacterItem)
        fun archiveddAt(pos: Int)
    }

    interface Controller {
        fun createCharacter(characterName: String)
        fun archiveCharacter(pos: Int, item: CharacterItem)
    }
}

class CharacterItem(val id: Long, val name: String, val hp: Int, val lastUsed: Date, val skills: List<Pair<String, Int>>, val things: List<Pair<String, Int>>, var index: Int = -1)
