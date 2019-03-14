package com.helloandroid.list_worlds

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.helloandroid.App
import com.helloandroid.list_games.ListGamesController
import ru.napoleonit.talan.di.ControllerInjector
import javax.inject.Inject

class ListWorldsController : Controller(), ListWorldsContract.Controller {

    @Inject
    lateinit var view: ListWorldsContract.View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return view.createView(container)
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        this.view.setData(App.instance.worlds.map { it.name })
    }

    override fun onItemClick(pos: Int) {
        router.pushController(RouterTransaction.with(ListGamesController(App.instance.worlds[pos].id)))
    }

}
