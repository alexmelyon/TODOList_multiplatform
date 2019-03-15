package com.helloandroid.session

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.helloandroid.App
import com.helloandroid.list_games.WORLD_KEY
import com.helloandroid.list_sessions.GAME_KEY
import ru.napoleonit.talan.di.ControllerInjector
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

val SESSION_KEY = "SESSION_KEY"

class SessionController(args: Bundle) : Controller(args), SessionContract.Controller {

    @Inject
    lateinit var view: SessionContract.View

    val world = App.instance.worlds.first { it.id == args.getInt(WORLD_KEY) }
    val game = App.instance.games.first { it.id == args.getInt(GAME_KEY) && it.worldGroup == world.id }
    val session = App.instance.gameSessions.first { it.id == args.getInt(SESSION_KEY) && it.gameGroup == game.id && it.worldGroup == world.id }

    constructor(sessionId: Int, gameId: Int, worldId: Int) : this(Bundle().apply {
        putInt(SESSION_KEY, sessionId)
        putInt(GAME_KEY, gameId)
        putInt(WORLD_KEY, worldId)
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return view.createView(container)
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
    }

    override fun getSessionDatetime(): String {
        return session.startTime.let { SimpleDateFormat("d MMM HH:mm", Locale.getDefault()).format(it) }
    }
}