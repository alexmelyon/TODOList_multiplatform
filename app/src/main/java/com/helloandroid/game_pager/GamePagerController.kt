package com.helloandroid.game_pager


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.*
import android.widget.LinearLayout
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.support.RouterPagerAdapter
import com.helloandroid.MainActivity
import com.helloandroid.R
import com.helloandroid.list_characters.ListCharactersController
import com.helloandroid.list_games.WORLD_KEY
import com.helloandroid.list_sessions.GAME_KEY
import com.helloandroid.list_sessions.ListSessionsController
import com.helloandroid.room.AppDatabase
import com.helloandroid.room.Game
import com.helloandroid.room.World
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.viewPager
import org.jetbrains.anko.wrapContent
import ru.napoleonit.talan.di.ControllerInjector
import java.lang.ref.WeakReference
import javax.inject.Inject

class GamePagerController(args: Bundle) : Controller(args) {

    private lateinit var world: World
    private lateinit var game: Game

    constructor(worldId: Long, gameId: Long) : this(Bundle().apply {
        putLong(WORLD_KEY, worldId)
        putLong(GAME_KEY, gameId)
    })

    @Inject
    lateinit var db: AppDatabase

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    val pagerAdapter: RouterPagerAdapter
    lateinit var listCharactersController: ListCharactersController
    lateinit var screenToController: List<Pair<String, Controller>>
    var selectedTab = 0
    lateinit var menu: Menu
    lateinit var menuInflater: MenuInflater

    init {
        pagerAdapter = object : RouterPagerAdapter(this) {
            override fun configureRouter(router: Router, position: Int) {
                if (!router.hasRootController()) {
                    val page = screenToController[position].second
                    router.setRoot(RouterTransaction.with(page))
                }
            }

            override fun getCount(): Int {
                return 2
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return screenToController[position].first
            }
        }
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)

        world = db.worldDao().getWorldById(args.getLong(WORLD_KEY))
        game = db.gameDao().getAll(args.getLong(GAME_KEY), world.id)
        listCharactersController = ListCharactersController(world. id, game.id)
        screenToController = listOf(
            "Characters" to listCharactersController,
            "Sessions" to ListSessionsController(world.id, game.id).apply {
                delegate = WeakReference(listCharactersController)
            })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        (activity as MainActivity).supportActionBar!!.title = game.name
        setHasOptionsMenu(true)

        val view = container.context.linearLayout {
            orientation = LinearLayout.VERTICAL
            this@GamePagerController.tabLayout = tabLayout {
                minimumHeight = R.attr.actionBarSize
                setTabTextColors(Color.BLACK, Color.BLACK)
            }.lparams(matchParent, wrapContent)
            this@GamePagerController.viewPager = viewPager {
                adapter = pagerAdapter
            }.lparams(matchParent, matchParent)
        }
        tabLayout.setupWithViewPager(viewPager)
        return view
    }

    override fun onDestroyView(view: View) {
        if (activity?.isChangingConfigurations() ?: false) {
            viewPager.adapter = null
        }
        tabLayout.setupWithViewPager(null)
        super.onDestroyView(view)
    }

    val tabselectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            selectedTab = tab.position
            screenToController[tab.position].second.onCreateOptionsMenu(menu, menuInflater)
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
            selectedTab = tab.position
            screenToController[tab.position].second.onCreateOptionsMenu(menu, menuInflater)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        this.menu = menu
        this.menuInflater = inflater

        tabLayout.addOnTabSelectedListener(tabselectedListener)
        tabLayout.getTabAt(selectedTab)?.select()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return screenToController[tabLayout.selectedTabPosition].second.onOptionsItemSelected(item)
    }
}