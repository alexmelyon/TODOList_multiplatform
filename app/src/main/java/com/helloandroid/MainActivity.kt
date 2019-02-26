package com.helloandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.StringParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verticalLayout {
            val name = editText()
            button("Say hello") {
                onClick { addUser(name.text.toString()) }
            }
            listView {

            }
            verticalLayout {
                getUsers().forEach { user ->
                    textView(user)
                }
            }
        }
    }

    fun addUser(name: String) {
        toast("Hello ${name}")
        database.use {
            insert("User", "name" to name)
        }
    }

    fun getUsers(): List<String> {
        return database.use {
            select("User", "name").parseList(StringParser)
        }
    }
}
