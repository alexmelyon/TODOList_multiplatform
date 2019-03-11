package com.example.todojs

import com.alexmelyon.todocore.TodoItem
import com.alexmelyon.todocore.TodoMvp
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.browser.localStorage

class TodoModel : TodoMvp.Model {
    val PREFS_SIZE = "PREFS_SIZE"
    val todoList: MutableList<TodoItem> by lazy { loadList() }
    override fun getList(): MutableList<TodoItem> {
        return todoList
    }

    override fun saveList() {
        for (i in 0 until todoList.size) {
            localStorage["${i}b"] = todoList[i].checked.toString()
            localStorage["${i}s"] = todoList[i].text
        }
        localStorage[PREFS_SIZE] = todoList.size.toString()
    }

    private fun loadList(): MutableList<TodoItem> {
        val size = localStorage[PREFS_SIZE]?.toInt() ?: 0
        val list = mutableListOf<TodoItem>()
        for (i in 0 until size) {
            val isChecked = localStorage["${i}b"]?.toBoolean() ?: false
            val text = localStorage["${i}s"] ?: ""
            list.add(TodoItem(isChecked, text))
        }
        return list
    }
}