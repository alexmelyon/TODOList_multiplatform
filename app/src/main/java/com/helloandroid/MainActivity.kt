package com.helloandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import kotlinx.android.synthetic.main.activity_main.*

const val PREFS_SIZE = "PREFS_SIZE"

class MainActivity : AppCompatActivity(), TodoMvp.View {

    val presenter: TodoMvp.Presenter = TodoPresenter(TodoModel(this))

    lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodoAdapter()
        adapter.onChecked { pos, checked ->
            presenter.setItemChecked(pos, checked)
        }
        adapter.onTextChanged { pos, text ->
            presenter.setItemText(pos, text)
        }
        adapter.onAddItem { pos ->
            presenter.add()
        }
        recyclerView.adapter = adapter
        recyclerView.setOnSwipeLeft { pos ->
            presenter.remove(pos)
        }
    }

    fun RecyclerView.setOnSwipeLeft(action: (Int) -> Unit) {
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                action(viewHolder.adapterPosition)
            }
        }.let { ItemTouchHelper(it).attachToRecyclerView(recyclerView) }
    }

    override fun added() {
        adapter.added()
    }

    override fun removedAt(position: Int) {
        adapter.removedAt(position)
    }

    override fun setList(list: MutableList<TodoItem>) {
        adapter.values = list
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
        presenter.getList()
    }

    override fun onPause() {
        super.onPause()
        presenter.saveList()
    }


}
