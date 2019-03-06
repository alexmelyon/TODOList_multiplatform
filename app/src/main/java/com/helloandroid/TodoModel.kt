package com.helloandroid

import android.content.Context

class TodoModel(val context: Context) : TodoMvp.Model {

    val PREF_NAME = "TODO_LIST"
    val todoList: MutableList<TodoItem> by lazy { loadList() }

    override fun getList(): MutableList<TodoItem> {
        return todoList
    }

    override fun saveList() {
        val list = todoList
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().apply {
            for (i in 0 until list.size) {
                putBoolean("${i}b", list[i].checked)
                putString("${i}s", list[i].text)
            }
            putInt(PREFS_SIZE, list.size)
            apply()
        }
    }

    private fun loadList(): MutableList<TodoItem> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val size = prefs.getInt(PREFS_SIZE, 0)
        val list = mutableListOf<TodoItem>()
        for (i in 0 until size) {
            val isChecked = prefs.getBoolean("${i}b", false)
            val text = prefs.getString("${i}s", "") ?: ""
            list.add(TodoItem(isChecked, text))
        }
        return list
    }
}