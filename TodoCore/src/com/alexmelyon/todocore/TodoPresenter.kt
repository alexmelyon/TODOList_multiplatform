package com.alexmelyon.todocore

class TodoPresenter(val model: TodoMvp.Model) : TodoMvp.Presenter {

    private lateinit var view: TodoMvp.View

    override fun setView(view: TodoMvp.View) {
        this.view = view
    }

    override fun add() {
        model.getList().add(TodoItem())
        view.addedAt(model.getList().size - 1)
    }

    override fun removeAt(position: Int) {
        model.getList().removeAt(position)
        view.removedAt(position)
    }

    override fun saveList() {
        model.saveList()
    }

    override fun getList() {
        this.view.setList(model.getList())
    }

    override fun setItemChecked(pos: Int, checked: Boolean) {
        model.getList()[pos].checked = checked
    }

    override fun setItemText(pos: Int, text: String) {
        model.getList()[pos].text = text
    }

}