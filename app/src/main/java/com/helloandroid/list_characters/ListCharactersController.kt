package com.helloandroid.list_characters

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.helloandroid.list_games.WORLD_KEY
import com.helloandroid.list_sessions.GAME_KEY
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textView

class ListCharactersController(args: Bundle) : Controller(args) {

    constructor(worldId: Int, gameId: Int) : this(Bundle().apply {
        putInt(WORLD_KEY, worldId)
        putInt(GAME_KEY, gameId)
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return container.context.frameLayout {
            textView("This is ListCharactersController") {
                textColor = Color.BLACK
            }
        }
    }
}