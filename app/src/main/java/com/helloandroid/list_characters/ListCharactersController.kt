package com.helloandroid.list_characters

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import com.bluelinelabs.conductor.Controller
import com.helloandroid.App
import com.helloandroid.Character
import com.helloandroid.R
import com.helloandroid.list_games.WORLD_KEY
import com.helloandroid.list_sessions.GAME_KEY
import com.helloandroid.room.AppDatabase
import com.helloandroid.room.Game
import com.helloandroid.room.World
import ru.napoleonit.talan.di.ControllerInjector
import java.util.*
import javax.inject.Inject

interface ListCharactersDelegate {
    fun updateCharactersScreen(activity: Activity)
}

class ListCharactersController(args: Bundle) : Controller(args), ListCharactersContract.Controller, ListCharactersDelegate {

    @Inject
    lateinit var view: ListCharactersContract.View
    @Inject
    lateinit var db: AppDatabase

    lateinit var world: World
    lateinit var game: Game
    private val characterItems = TreeSet(Comparator<CharacterItem> { o1, o2 ->
        return@Comparator o1.name.compareTo(o2.name)
    })

    constructor(worldId: Int, gameId: Int) : this(Bundle().apply {
        putInt(WORLD_KEY, worldId)
        putInt(GAME_KEY, gameId)
    })

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)
        world = db.worldDao().getWorldById(args.getInt(WORLD_KEY))
        game = db.gameDao().getAll(args.getInt(GAME_KEY), world.id)
        updateScreen()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return view.createView(container)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.list_characters, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_add_character -> {
                view.showAddCharacterDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updateCharactersScreen(activity: Activity) {
        updateScreen()
    }

    fun updateScreen() {
        characterItems.clear()
        val characters = App.instance.characters.filter { it.gameGroup == game.id && it.worldGroup == world.id }
            .filterNot { it.archived }
        val closedSessions = App.instance.gameSessions.filter { it.gameGroup == game.id && it.worldGroup == world.id }
            .filterNot { it.open }
            .map { it.id }
        characters.forEach { character ->
            val hp = closedSessions.fold(0) { total, next ->
                App.instance.hpDiffs.filter { it.characterGroup == character.id && closedSessions.contains(it.sessionGroup) && it.gameGroup == game.id && it.worldGroup == world.id }
                    .sumBy { it.value }
            }

            val skills = db.skillDao().getAll(world.id, archived = false)
            val skillsDiffs = App.instance.skillDiffs.filter { it.characterGroup == character.id && closedSessions.contains(it.sessionGroup) && it.gameGroup == game.id && it.worldGroup == world.id }
                .fold(listOf<Pair<Int, Int>>()) { total, next ->
                    total + listOf(Pair(next.skillGroup, next.value))
                }.map { skillTovalue -> skills.single { it.id == skillTovalue.first }.name to skillTovalue.second }
                .groupBy { it.first }
                .map { it.key to it.value.sumBy { it.second } }
                .filter { it.second != 0}

            val things = App.instance.things.filter { it.worldGroup == world.id }
            // TODO Refactor this boilerplate
            val thingDiffs = App.instance.thingDiffs.filter { it.characterGroup == character.id && closedSessions.contains(it.sessionGroup) && it.gameGroup == game.id && it.worldGroup == world.id }
                .fold(listOf<Pair<Int, Int>>()) { total, next ->
                    total + listOf(Pair(next.thingGroup, next.value))
                }.map { thingTovalue -> things.single { it.id == thingTovalue.first }.name to thingTovalue.second }
                .groupBy { it.first }
                .map { it.key to it.value.sumBy { it.second } }
                .filter { it.second != 0}

            characterItems.add(CharacterItem(character.id, character.name, hp, skillsDiffs, thingDiffs))
            characterItems.forEachIndexed { index, item ->
                item.index = index
            }
        }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        this.view.setData(characterItems.toMutableList())
    }

    override fun createCharacter(characterName: String) {
        val maxId = App.instance.characters.filter { it.gameGroup == game.id && it.worldGroup == world.id }
            .maxBy { it.id }?.id ?: -1
        val character = Character(maxId + 1, characterName, game.id, world.id, archived = false)
        App.instance.characters.add(character)

        val item = CharacterItem(character.id, characterName, 0, listOf(), listOf())
        characterItems.add(item)
        characterItems.forEachIndexed { index, item ->
            item.index = index
        }
        view.addedAt(item.index, item)
    }

    override fun archiveCharacter(pos: Int, item: CharacterItem) {
        val character = App.instance.characters.filter { it.gameGroup == game.id && it.worldGroup == world.id }
            .single { it.id == item.id }
        character.archived = true

        characterItems.remove(item)
        characterItems.forEachIndexed { index, item ->
            item.index = index
        }
        view.archiveddAt(pos)
    }
}