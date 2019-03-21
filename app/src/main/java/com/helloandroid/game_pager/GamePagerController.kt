package com.helloandroid.game_pager


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
import com.helloandroid.App
import com.helloandroid.MainActivity
import com.helloandroid.R
import com.helloandroid.list_characters.ListCharactersController
import com.helloandroid.list_games.WORLD_KEY
import com.helloandroid.list_sessions.GAME_KEY
import com.helloandroid.list_sessions.ListSessionsController
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.viewPager
import org.jetbrains.anko.wrapContent

class GamePagerController(args: Bundle) : Controller(args) {

    private val world = App.instance.worlds.first { it.id == args.getInt(WORLD_KEY) }
    private val game = App.instance.games.first { it.id == args.getInt(GAME_KEY) && it.worldGroup == world.id }

    //
    constructor(worldId: Int, gameId: Int) : this(Bundle().apply {
        putInt(WORLD_KEY, worldId)
        putInt(GAME_KEY, gameId)
    })

    //
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    val pagerAdapter: RouterPagerAdapter
    val listCharactersController = ListCharactersController(world.id, game.id)
    var selectedTab = 0
    val listPages = listOf(
        "Characters" to ListCharactersController(world.id, game.id),
        "Sessions" to ListSessionsController(world.id, game.id).apply {
//            delegate = WeakReference(listCharactersController)
        })
    lateinit var menu: Menu
    lateinit var menuInflater: MenuInflater

    init {
        pagerAdapter = object : RouterPagerAdapter(this) {
            override fun configureRouter(router: Router, position: Int) {
                if (!router.hasRootController()) {
                    val page = listPages[position].second
                    router.setRoot(RouterTransaction.with(page))
                }
            }

            override fun getCount(): Int {
                return 2
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return listPages[position].first
            }
        }
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
        tabLayout.getTabAt(selectedTab)?.select()
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
            listPages[tab.position].second.onCreateOptionsMenu(menu, menuInflater)
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
            listPages[tab.position].second.onCreateOptionsMenu(menu, menuInflater)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        this.menu = menu
        this.menuInflater = inflater

        tabLayout.addOnTabSelectedListener(tabselectedListener)
        tabLayout.getTabAt(tabLayout.selectedTabPosition)?.select()
    }

    // FIXME Если есть, то createCharacter вызывается дважды
    // Если нет, то createSession не вызывается
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return listPages[tabLayout.selectedTabPosition].second.onOptionsItemSelected(item)
//    }
}