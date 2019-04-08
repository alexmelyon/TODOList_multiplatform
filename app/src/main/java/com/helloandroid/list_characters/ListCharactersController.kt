package com.helloandroid.list_characters

import android.content.Context
import android.os.Bundle
import android.view.*
import com.bluelinelabs.conductor.Controller
import com.helloandroid.R
import com.helloandroid.list_games.WORLD_KEY
import com.helloandroid.list_sessions.GAME_KEY
import com.helloandroid.room.AppDatabase
import com.helloandroid.room.Game
import com.helloandroid.room.GameCharacter
import com.helloandroid.room.World
import ru.napoleonit.talan.di.ControllerInjector
import java.util.*
import javax.inject.Inject

interface ListCharactersDelegate {
    fun updateCharactersScreen()
}

class ListCharactersController(args: Bundle) : Controller(args), ListCharactersContract.Controller, ListCharactersDelegate {

    @Inject
    lateinit var view: ListCharactersContract.View
    @Inject
    lateinit var db: AppDatabase

    lateinit var world: World
    lateinit var game: Game
    private val characterItems = TreeSet(Comparator<CharacterItem> { o1, o2 ->
        if (o1.lastUsed != o2.lastUsed) {
            return@Comparator o1.lastUsed.compareTo(o2.lastUsed)
        }
        return@Comparator o1.name.compareTo(o2.name)
    })

    constructor(worldId: Long, gameId: Long) : this(Bundle().apply {
        putLong(WORLD_KEY, worldId)
        putLong(GAME_KEY, gameId)
    })

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)
        world = db.worldDao().getWorldById(args.getLong(WORLD_KEY))
        game = db.gameDao().getAll(args.getLong(GAME_KEY), world.id)
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
        when (item.itemId) {
            R.id.menu_add_character -> {
                view.showAddCharacterDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updateCharactersScreen() {
        updateScreen()
    }

    fun updateScreen() {
        characterItems.clear()
        val characters = db.characterDao().getAll(world.id, game.id, archived = false)
        val closedSessions = db.gameSessionDao().getAll(world.id, game.id, archived = false)
            .filterNot { it.open }
            .map { it.id }
        characters.forEach { character ->
            val hp = db.hpDiffDao().getAllByCharacter(world.id, game.id, character.id, archived = false)
                .filter { closedSessions.contains(it.sessionGroup) }
                .sumBy { it.value }

            val skills = db.skillDao().getAll(world.id, archived = false)
            val skillDiffs = db.skillDiffDao().getAllByCharacter(world.id, game.id, character.id, archived = false)
                .asSequence()
                .filter { closedSessions.contains(it.sessionGroup) }
                .fold(listOf<Pair<Long, Int>>()) { total, next ->
                    total + listOf(Pair(next.skillGroup, next.value))
                }.map { skillTovalue -> skills.single { it.id == skillTovalue.first } to skillTovalue.second }
                .groupBy { it.first }
                .map { it.key to it.value.sumBy { it.second } }
                .filter { it.second != 0 }
                .toList()
            val skillDiffNames = skillDiffs.map { it.first.name to it.second }

            val things = db.thingDao().getAll(world.id, archived = false)
            // TODO Refactor this boilerplate
            val thingDiffs = db.thingDiffDao().getAllByCharacter(world.id, game.id, character.id, archived = false)
                .asSequence()
                .filter { closedSessions.contains(it.sessionGroup) }
                .fold(listOf<Pair<Long, Int>>()) { total, next ->
                    total + listOf(Pair(next.thingGroup, next.value))
                }.map { thingToValue -> things.single { it.id == thingToValue.first } to thingToValue.second }
                .groupBy { it.first }
                .map { it.key to it.value.sumBy { it.second } }
                .filter { it.second != 0 }
                .toList()
            val thingDiffNames = thingDiffs.map { it.first.name to it.second }

            val lastUsed = (skillDiffs.map { it.first.lastUsed } + thingDiffs.map { it.first.lastUsed })
                .min() ?: Calendar.getInstance().time
            characterItems.add(CharacterItem(character.id, character.name, hp, lastUsed, skillDiffNames, thingDiffNames))
            characterItems.forEachIndexed { index, item ->
                item.index = index
            }
        }
        if (isAttached) {
            this.view.setData(characterItems.toMutableList())
        }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        this.view.setData(characterItems.toMutableList())
    }

    override fun createCharacter(characterName: String) {
        val character = GameCharacter(characterName, game.id, world.id, archived = false)
        val id = db.characterDao().insert(character)
        character.id = id

        val item = CharacterItem(character.id, characterName, 0, Calendar.getInstance().time, listOf(), listOf())
        characterItems.add(item)
        characterItems.forEachIndexed { index, item ->
            item.index = index
        }
        view.addedAt(item.index, item)
    }

    override fun archiveCharacter(pos: Int, item: CharacterItem) {
        val character = db.characterDao().get(world.id, game.id, item.id)
        character.archived = true
        db.characterDao().update(character)

        characterItems.remove(item)
        characterItems.forEachIndexed { index, item ->
            item.index = index
        }
        view.archiveddAt(pos)
    }
}