package com.helloandroid.list_characters

import android.view.ViewGroup

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

class CharacterItem(val id: Int, val name: String, val hp: Int, val skills: List<Pair<String, Int>>, val things: List<Pair<String, Int>>, var index: Int = -1)
