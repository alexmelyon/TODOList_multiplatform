package com.example.todojs

import com.alexmelyon.todocore.TodoPresenter
import org.w3c.dom.HTMLButtonElement
import kotlin.browser.document
import kotlin.browser.window

fun main() {
    val presenter = TodoPresenter(TodoModel())
    val view = TodoView(presenter)

    window.onload = {
        presenter.setView(view)
        presenter.getList()
    }
    window.onunload = {
        presenter.saveList()
    }
    val addButton = document.getElementById("addButton") as HTMLButtonElement
    addButton.onclick = { presenter.add() }
}
