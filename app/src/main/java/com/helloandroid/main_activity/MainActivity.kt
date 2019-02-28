package com.helloandroid.main_activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.helloandroid.App
import com.helloandroid.R
import com.helloandroid.World
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk15.listeners.onItemClick

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    lateinit var worlds: List<World>
    val log = AnkoLogger<MainActivity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        worlds = App.instance.worlds

        verticalLayout {
            listView {
                adapter =
                    ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, worlds.map { it.name })
                onItemClick { adapter, view, pos, id ->
                    log.info { "ON CLICK $pos, ${adapter?.getItemAtPosition(pos)}" }
                }
            }.lparams(matchParent, matchParent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.first_item -> log.info { "FIRST ITEM" }
            R.id.second_item -> log.info { "SECOND ITEM" }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}
