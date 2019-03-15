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
        val characters = App.instance.characters.filter { it.gameGroup == game.id && it.worldGroup == world.id }
        fun getCharacter(characterId: Int) = characters.single { it.id == characterId  }
        val skills = App.instance.skills.filter { it.worldGroup == world.id }
        fun getSkill(skillId: Int) = skills.single { it.id == skillId }
        val things = App.instance.things.filter { it.worldGroup == world.id }
        fun getThing(thingId: Int) = things.single { it.id == thingId }
        val list = App.instance.hpDiffs.filter { it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }.map { SessionItem(SessionItemType.ITEM_HP, it.id, "HP", getCharacter(it.characterGroup).name, it.value) } +
                App.instance.skillDiffs.filter { it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }.map { SessionItem(SessionItemType.ITEM_SKILL, it.id, getSkill(it.skillGroup).name, getCharacter(it.characterGroup).name, it.value) } +
                App.instance.thingDiffs.filter { it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }.map { SessionItem(SessionItemType.ITEM_SKILL, it.id, getThing(it.thingGroup).name, getCharacter(it.characterGroup).name, it.value) } +
                App.instance.commentDiffs.filter { it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }.map { SessionItem(SessionItemType.ITEM_COMMENT, it.id, "", "", 0) }

        // TODO Order date descending
        this.view.setData(list.toMutableList())
    }

    override fun getSessionDatetime(): String {
        return session.startTime.let { SimpleDateFormat("d MMMM HH:mm", Locale.getDefault()).format(it) }
    }

    override fun onHpChanged(id: Int, value: Int) {
        // TODO update view
        val hpDiff = App.instance.hpDiffs.single { it.id == id && it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }
        hpDiff.value += value
    }

    override fun onSkillChanged(id: Int, value: Int) {
        val skillDiff = App.instance.skillDiffs.single { it.id == id && it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }
        skillDiff.value += value
    }

    override fun onThingChanged(id: Int, value: Int) {
        val thingDiff = App.instance.thingDiffs.single { it.id == id && it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }
        thingDiff.value += value
    }

    override fun onCommentChanged(id: Int, comment: String) {
        val commentDiff = App.instance.commentDiffs.single { it.id == id && it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }
        commentDiff.comment = comment
    }
}