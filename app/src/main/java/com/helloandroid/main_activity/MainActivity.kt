package com.helloandroid.main_activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.helloandroid.*
import com.helloandroid.preview_activity.PreviewActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk15.listeners.onItemClick

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    val worlds: List<World> by lazy { App.instance.worlds }
    val games: List<Game> by lazy { App.instance.games }
    val sessions: List<GameSession> by lazy { App.instance.gameSessions }
    val log = AnkoLogger<MainActivity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        log.info { "INTENT LIST ${intent.extras}" }
        val worldId = intent.extras?.get("world") as Int?
        val gameId = intent.extras?.get("game") as Int?
        val sessionId = intent.extras?.get("session") as Int?
        var list = worlds.map { it.name }
        if(worldId != null) {
            list = games.filter { it.worldGroup == worldId }
                .map { it.name }

            if(gameId != null) {
                list = sessions.filter { it.worldGroup == worldId && it.gameGroup == gameId }
                    .map { it.name }
            }
        }

        verticalLayout {
            listView {
                adapter =
                    ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, list)
                onItemClick { adapter, view, pos, id ->
                    log.info { "ON CLICK $pos, ${adapter?.getItemAtPosition(pos)}" }
                    val args = mutableMapOf<String, Any?>()
                    if(worldId == null) {
                        args += "world" to pos
                    } else {
                        args += "world" to worldId
                        if(gameId == null) {
                            args += "game" to pos
                        } else {
                            args += "game" to gameId
                            if(sessionId == null) {
                                args += "session" to pos
                            } else {
                                args += "session" to sessionId
                            }
                        }
                    }
                    startActivity(intentFor<MainActivity>(*args.toList().toTypedArray()))
                }
            }.lparams(matchParent, matchParent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.first_item -> log.info { "FIRST ITEM" }
            R.id.preview_activity_item -> startActivity(intentFor<PreviewActivity>())
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}
