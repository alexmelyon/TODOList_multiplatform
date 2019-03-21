package com.helloandroid.list_sessions

import android.content.Context
import android.os.Bundle
import android.view.*
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.helloandroid.App
import com.helloandroid.GameSession
import com.helloandroid.R
import com.helloandroid.list_characters.ListCharactersDelegate
import com.helloandroid.list_games.WORLD_KEY
import com.helloandroid.session.SessionController
import ru.napoleonit.talan.di.ControllerInjector
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

val GAME_KEY = "GAME_KEY"

interface ListSessionsDelegate {
    fun updateScreenSessionClosed()
}

// TODO Headers for open and closed sessions
class ListSessionsController(args: Bundle) : Controller(args), ListSessionsContract.Controller, ListSessionsDelegate {

    @Inject
    lateinit var view: ListSessionsContract.View

    val world = App.instance.worlds.first { it.id == args.getInt(WORLD_KEY) }
    val game = App.instance.games.first { it.id == args.getInt(GAME_KEY) && it.worldGroup == world.id }
    var delegate: ListCharactersDelegate? = null

    private lateinit var sessionsList: MutableList<GameSession>

    constructor(worldId: Int, gameId: Int) : this(Bundle().apply {
        putInt(WORLD_KEY, worldId)
        putInt(GAME_KEY, gameId)
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        setHasOptionsMenu(true)
        return view.createView(container)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        val parent = parentController as GamePagerController
//        if (parent.selectedController != this) {
//            return
//        }
//        menu.clear()
//        inflater.inflate(R.menu.list_sessions, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_session -> view.showCreateSessionDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)

        updateScreen()
    }

    override fun updateScreenSessionClosed() {
        updateScreen()
        delegate?.updateCharactersScreen()
    }

    fun updateScreen() {
        sessionsList = App.instance.gameSessions.filter { it.worldGroup == world.id && it.gameGroup == game.id }
            .filterNot { it.archived }
            .sortedWith(Comparator { o1, o2 ->
                if (o1.open != o2.open) {
                    return@Comparator o2.open.compareTo(o1.open)
                }
                if (o1.open && o1.startTime != o2.startTime) {
                    return@Comparator o2.startTime.compareTo(o1.startTime)
                }
                if (!o1.open && o1.endTime != o2.endTime) {
                    return@Comparator o2.endTime.compareTo(o1.endTime)
                }
                return@Comparator o1.name.compareTo(o2.name)
            })
            .toMutableList()
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        this.view.setData(sessionsList)
    }

    override fun getDescription(pos: Int): String {
        val session = sessionsList[pos]
        return session.startTime.let { SimpleDateFormat("d MMMM HH:mm", Locale.getDefault()).format(it) }
    }

    override fun onItemClick(pos: Int) {
        val router = parentController?.router ?: this.router
        val sessionId = sessionsList[pos].id
        router.pushController(RouterTransaction.with(SessionController(sessionId, game.id, world.id).apply {
            delegate = this@ListSessionsController
        }))
    }

    override fun getGameName(): String {
        return game.name
    }

    override fun createSession(sessionName: String) {
        val maxId = App.instance.gameSessions.filter { it.gameGroup == game.id && it.worldGroup == world.id }
            .maxBy { it.id }?.id ?: -1
        val now = Calendar.getInstance().time
        val session = GameSession(maxId + 1, sessionName, game.id, world.id, now, false, now)
        App.instance.gameSessions.add(session)

        view.addedAt(0, session)
    }

    override fun archiveSession(pos: Int, item: GameSession) {
        val session = App.instance.gameSessions.single { it.id == item.id && it.gameGroup == game.id && it.worldGroup == world.id }
        session.archived = true

        view.archivedAt(pos)
    }
}