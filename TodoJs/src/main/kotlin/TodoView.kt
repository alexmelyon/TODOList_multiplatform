package com.example.todojs

import com.alexmelyon.todocore.TodoItem
import com.alexmelyon.todocore.TodoMvp
import com.alexmelyon.todocore.TodoPresenter
import kotlinx.html.button
import kotlinx.html.checkBoxInput
import kotlinx.html.dom.append
import kotlinx.html.dom.create
import kotlinx.html.input
import kotlinx.html.li
import org.w3c.dom.*
import kotlin.browser.document

class TodoView(val presenter: TodoPresenter) : TodoMvp.View {

    override fun addedAt(position: Int) {
        val listView = document.getElementById("list") as HTMLElement
        listView.append {
            liItem(listView, false, "", presenter)
        }
    }

    override fun removedAt(position: Int) {
        val listView = document.getElementById("list")!!
        listView.children[position]?.remove()
    }

    override fun setList(list: MutableList<TodoItem>) {
        list.forEachIndexed { index, item ->
            val listView = document.getElementById("list") as HTMLElement
            listView.append {
                liItem(listView, item.checked, item.text, presenter)
            }
        }
    }
}

fun liItem(container: HTMLElement, isChecked: Boolean, text: String, presenter: TodoPresenter) {
    val li = document.create.li {
        checkBoxInput(name = "checked") { checked = isChecked }
        input(name = "text") { value = text }
        button(name = "remove") { text("X") }
    }

    val checkbox = li.getElementsByTagName("input").asList().first() as HTMLInputElement
    checkbox.onclick = { e -> presenter.setItemChecked(getPosition(li), checkbox.checked) }
    val textItem = li.getElementsByTagName("input").asList()[1] as HTMLInputElement
    textItem.oninput = { e ->
        presenter.setItemText(getPosition(li), textItem.value)
    }
    val button = li.getElementsByTagName("button").asList().first() as HTMLButtonElement
    button.onclick = { presenter.removeAt(getPosition(li)) }
    container.appendChild(li)
}

fun getPosition(element: HTMLElement): Int {
    return document.getElementById("list")!!.children.asList().indexOf(element)
}