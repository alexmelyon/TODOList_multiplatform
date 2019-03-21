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

    val PAGE_CHARACTERS = 0
    val PAGE_SESSIONS = 1

    val world = App.instance.worlds.first { it.id == args.getInt(WORLD_KEY) }
    val game = App.instance.games.first { it.id == args.getInt(GAME_KEY) && it.worldGroup == world.id }
    var selectedTab = 0
    var selectedController: Controller? = null
    val listCharactersController = ListCharactersController(world.id, game.id)
    val childPages = mutableMapOf<Int, Controller>()
    lateinit var menu: Menu
    lateinit var menuInflater: MenuInflater

    constructor(worldId: Int, gameId: Int) : this(Bundle().apply {
        putInt(WORLD_KEY, worldId)
        putInt(GAME_KEY, gameId)
    })

    val pagerAdapter: RouterPagerAdapter
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout

    init {
        pagerAdapter = object : RouterPagerAdapter(this) {
            override fun configureRouter(router: Router, position: Int) {
                if (!router.hasRootController()) {
                    val page = when (position) {
                        PAGE_CHARACTERS -> listCharactersController
                        PAGE_SESSIONS -> ListSessionsController(world.id, game.id).apply {
                            delegate = listCharactersController
                        }
                        else -> throw IllegalArgumentException("Wrong page $position")
                    }
                    childPages[position] = page
                    if(selectedTab == position) {
                        selectedController = page
                    }
                    router.setRoot(RouterTransaction.with(page))
                }
            }

            override fun getCount(): Int {
                return 2
            }

            override fun getPageTitle(position: Int): CharSequence? {
                when(position) {
                    PAGE_CHARACTERS -> return "Characters"
                    PAGE_SESSIONS -> return "Sessions"
                }
                return super.getPageTitle(position)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        (activity as MainActivity).supportActionBar!!.title = game.name

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

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)

        val listener = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                println("onTabSelected")
                selectedTab = tab.position
                selectedController = childPages[tab.position]
                childPages[selectedTab]?.onCreateOptionsMenu(menu, menuInflater)
                setHasOptionsMenu(true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                println("onTabUnselected")
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                println("onTabReselected")
            }
        }
        tabLayout.addOnTabSelectedListener(listener)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        setHasOptionsMenu(true)
        tabLayout.getTabAt(selectedTab)?.select()
    }

    override fun onDestroyView(view: View) {
        if (activity?.isChangingConfigurations() ?: false) {
            viewPager.adapter = null
        }
        tabLayout.setupWithViewPager(null)
        super.onDestroyView(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        this.menu = menu
        this.menuInflater = inflater
    }
}