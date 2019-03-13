package com.helloandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.helloandroid.world.WorldController
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.info
import ru.napoleonit.talan.di.HasControllerInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasControllerInjector {

    @Inject
    lateinit var dispatchingControllerInjector: DispatchingAndroidInjector<Controller>

    lateinit var router: Router

    val worlds: List<World> by lazy { App.instance.worlds }
    val games: List<Game> by lazy { App.instance.games }
    val sessions: List<GameSession> by lazy { App.instance.gameSessions }
    val log = AnkoLogger<MainActivity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val content = find<ViewGroup>(android.R.id.content)
        router = Conductor.attachRouter(this, content, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(WorldController()))
        }

        log.info { "INTENT LIST ${intent.extras}" }
        val worldId = intent.extras?.get("world") as Int?
        val gameId = intent.extras?.get("game") as Int?
        val sessionId = intent.extras?.get("session") as Int?
        var list = worlds.map { it.name }
        if(worldId != null) {
            list = games.filter { it.worldGroup == worldId }
                .map { it.name }

            if(gameId != null) {
                list = sessions.filter { it.worldGroup == worldId && it.gameGroup == gameId }
                    .map { it.name }
            }
        }
    }

    override fun controllerInjector(): DispatchingAndroidInjector<Controller> {
        return dispatchingControllerInjector
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.first_item -> alert("FIRST ITEM").show()
//            R.id.preview_activity_item -> startActivity(intentFor<PreviewActivity>())
//            else -> return super.onOptionsItemSelected(item)
//        }
//        return true
//    }
}
