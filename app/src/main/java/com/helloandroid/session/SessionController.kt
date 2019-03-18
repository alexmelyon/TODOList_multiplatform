package com.helloandroid.session

import android.content.Context
import android.os.Bundle
import android.view.*
import com.bluelinelabs.conductor.Controller
import com.helloandroid.App
import com.helloandroid.HealthPointDiff
import com.helloandroid.R
import com.helloandroid.list_games.WORLD_KEY
import com.helloandroid.list_sessions.GAME_KEY
import ru.napoleonit.talan.di.ControllerInjector
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.Comparator

val SESSION_KEY = "SESSION_KEY"

class SessionController(args: Bundle) : Controller(args), SessionContract.Controller {

    @Inject
    lateinit var view: SessionContract.View

    val world = App.instance.worlds.first { it.id == args.getInt(WORLD_KEY) }
    val game = App.instance.games.first { it.id == args.getInt(GAME_KEY) && it.worldGroup == world.id }
    val session = App.instance.gameSessions.first { it.id == args.getInt(SESSION_KEY) && it.gameGroup == game.id && it.worldGroup == world.id }

    private lateinit var sessionItems: TreeSet<SessionItem>

    constructor(sessionId: Int, gameId: Int, worldId: Int) : this(Bundle().apply {
        putInt(SESSION_KEY, sessionId)
        putInt(GAME_KEY, gameId)
        putInt(WORLD_KEY, worldId)
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        setHasOptionsMenu(true)
        return view.createView(container)
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        val characters = App.instance.characters.filter { it.gameGroup == game.id && it.worldGroup == world.id }
        fun getCharacter(characterId: Int) = characters.single { it.id == characterId }
        val skills = App.instance.skills.filter { it.worldGroup == world.id }
        fun getSkill(skillId: Int) = skills.single { it.id == skillId }
        val things = App.instance.things.filter { it.worldGroup == world.id }
        fun getThing(thingId: Int) = things.single { it.id == thingId }
//        val list = App.instance.hpDiffs.filter { it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }.map { SessionItem(it.time, SessionItemType.ITEM_HP, it.id, "HP", getCharacter(it.characterGroup).name, it.value) } +
//                App.instance.skillDiffs.filter { it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }.map { SessionItem(it.time, SessionItemType.ITEM_SKILL, it.id, getSkill(it.skillGroup).name, getCharacter(it.characterGroup).name, it.value) } +
//                App.instance.thingDiffs.filter { it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }.map { SessionItem(it.time, SessionItemType.ITEM_SKILL, it.id, getThing(it.thingGroup).name, getCharacter(it.characterGroup).name, it.value) } +
//                App.instance.commentDiffs.filter { it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }.map { SessionItem(it.time, SessionItemType.ITEM_COMMENT, it.id, "", "", 0) }

        // TODO Order date descending

        sessionItems = TreeSet<SessionItem>(Comparator { o1, o2 ->
            val res = o2.time.compareTo(o1.time)
            if (res == 0) {
                return@Comparator o1.type.ordinal.compareTo(o2.type.ordinal)
            }
            return@Comparator res
        })
        sessionItems.addAll(App.instance.hpDiffs.filter { it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }
            .map { SessionItem(it.id, it.time, SessionItemType.ITEM_HP, "HP", getCharacter(it.characterGroup).name, it.value, it.characterGroup) })
        sessionItems.addAll(App.instance.skillDiffs.filter { it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }
            .map { SessionItem(it.id, it.time, SessionItemType.ITEM_SKILL, getSkill(it.skillGroup).name, getCharacter(it.characterGroup).name, it.value, it.characterGroup) })
        sessionItems.addAll(App.instance.thingDiffs.filter { it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }
            .map { SessionItem(it.id, it.time, SessionItemType.ITEM_THING, getThing(it.thingGroup).name, getCharacter(it.characterGroup).name, it.value, it.characterGroup) })
        sessionItems.addAll(App.instance.commentDiffs.filter { it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }
            .map { SessionItem(it.id, it.time, SessionItemType.ITEM_COMMENT, "", "", 0, -1, it.comment) })

        sessionItems.forEachIndexed { index, item -> item.index = index }
        this.view.setData(sessionItems.toMutableList())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.session_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val characterNames = App.instance.characters.filter { it.gameGroup == game.id && it.worldGroup == world.id }.map { it.name }.sorted()
        when(item.itemId) {
            R.id.session_add_hp -> view.showAddHpDialog(characterNames)
            R.id.session_add_skill -> view.showAddSkillDialog(characterNames)
            R.id.session_add_thing -> view.showAddThingDialog(characterNames)
            R.id.session_add_comment -> view.addComment()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getSessionDatetime(): String {
        return session.startTime.let { SimpleDateFormat("d MMMM HH:mm", Locale.getDefault()).format(it) }
    }

    override fun onHpChanged(pos: Int, value: Int) {
        val item = sessionItems.toList()[pos]
        item.value += value
        val hpId = item.id
        val characterId = item.characterId
        val hpDiff = App.instance.hpDiffs.single { it.id == hpId && it.sessionGroup == session.id && it.characterGroup == characterId && it.gameGroup == game.id && it.worldGroup == world.id }
        hpDiff.value += value
        this.view.itemChangedAt(pos)
    }

    override fun onSkillChanged(pos: Int, value: Int) {
        val item = sessionItems.toList()[pos]
        item.value += value
        val skillId = item.id
        val characterId = item.characterId
        val skillDiff = App.instance.skillDiffs.single { it.id == skillId && it.characterGroup == characterId && it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }
        skillDiff.value += value
        this.view.itemChangedAt(pos)
    }

    override fun onThingChanged(pos: Int, value: Int) {
        val item = sessionItems.toList()[pos]
        item.value += value
        val thingId = item.id
        val characterId = item.characterId
        val thingDiff = App.instance.thingDiffs.single { it.id == thingId && it.characterGroup == characterId && it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }
        thingDiff.value += value
        this.view.itemChangedAt(pos)
    }

    override fun onCommentChanged(pos: Int, comment: String) {
        val item = sessionItems.toList()[pos]
        val commentId = item.id
        item.comment = comment
        val commentDiff = App.instance.commentDiffs.single { it.id == commentId && it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }
        commentDiff.comment = comment
        // Do not itemChangedAt
    }

    override fun addHpDiff(which: Int) {
        val characters = App.instance.characters.filter { it.gameGroup == game.id && it.worldGroup == world.id }.sortedBy { it.name }
        val selectedCharacter = characters[which]
        val maxId = App.instance.hpDiffs.filter { it.sessionGroup == session.id && it.gameGroup == game.id && it.worldGroup == world.id }
            .maxBy { it.id }?.id ?: -1
        val hpDiff = HealthPointDiff(maxId + 1, 0, Calendar.getInstance().time, selectedCharacter.id, session.id, game.id, world.id)
        App.instance.hpDiffs.add(hpDiff)
        val item = SessionItem(hpDiff.id, hpDiff.time, SessionItemType.ITEM_HP, "HP", selectedCharacter.name, 0, selectedCharacter.id, "")
        sessionItems.add(item)
        sessionItems.forEachIndexed { index, sessionItem -> sessionItem.index = index }
        this.view.itemAddedAt(item.index, item)
    }
}