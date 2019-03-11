package com.alexmelyon.todocore

interface TodoMvp {
    interface View {
        fun setList(list: MutableList<TodoItem>)
        fun addedAt(position: Int)
        fun removedAt(position: Int)
    }
    interface Presenter {
        fun setView(view: View)
        fun add()
        fun removeAt(position: Int)
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