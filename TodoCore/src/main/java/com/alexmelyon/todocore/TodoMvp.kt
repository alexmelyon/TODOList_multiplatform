package com.alexmelyon.todocore

interface TodoMvp {
    interface View {
        fun setList(list: MutableList<TodoItem>)
        fun added()
        fun removedAt(position: Int)
    }
    interface Presenter {
        fun setView(view: View)
        fun add()
        fun remove(position: Int)
        fun saveList()
        fun getList()
        fun setItemChecked(pos: Int, checked: Boolean)
        fun setItemText(pos: Int, text: String)
    }
    interface Model {
        fun saveList()
        fun getList(): MutableList<TodoItem>
    }
}