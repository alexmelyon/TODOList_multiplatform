package com.helloandroid.world_pager

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.*
import android.widget.LinearLayout
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.support.RouterPagerAdapter
import com.helloandroid.App
import com.helloandroid.R
import com.helloandroid.list_games.ListGamesController
import com.helloandroid.list_games.WORLD_KEY
import com.helloandroid.list_skills.ListSkillsController
import com.helloandroid.list_things.ListThingsController
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.viewPager
import org.jetbrains.anko.wrapContent

class WorldPagerController(args: Bundle) : Controller(args) {

    val PAGE_GAMES = 0
    val PAGE_SKILLS = 1
    val PAGE_THINGS = 2

    val worldId = App.instance.worlds.single { it.id == args.getInt(WORLD_KEY) }.id

    private var menu: Menu? = null
    private var menuInflater: MenuInflater? = null

    constructor(worldId: Int) : this(Bundle().apply {
        putInt(WORLD_KEY, worldId)
    })

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private val pagerAdapter: PagerAdapter
    private val listPages = listOf(ListGamesController(worldId), ListSkillsController(worldId), ListThingsController(worldId))

    init {
        pagerAdapter = object : RouterPagerAdapter(this) {
            override fun configureRouter(router: Router, position: Int) {
                if (!router.hasRootController()) {
                    val page = listPages[position]
                    router.setRoot(RouterTransaction.with(page))
                }
            }

            override fun getCount(): Int {
                return 3
            }

            override fun getPageTitle(position: Int): CharSequence? {
                when(position) {
                    PAGE_GAMES -> return "Games"
                    PAGE_SKILLS -> return "Skills"
                    PAGE_THINGS -> return "Things"
                }
                return super.getPageTitle(position)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        setHasOptionsMenu(true)

        val view = container.context.linearLayout {
            orientation = LinearLayout.VERTICAL
            this@WorldPagerController.tabLayout = tabLayout {
                minimumHeight = R.attr.actionBarSize
                setTabTextColors(Color.BLACK, Color.BLACK)
            }.lparams(matchParent, wrapContent)
            this@WorldPagerController.viewPager = viewPager {
                adapter = pagerAdapter
            }.lparams(matchParent, matchParent)
        }
        tabLayout.setupWithViewPager(viewPager)
        return view
    }

    val tabselectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            listPages[tab.position].onCreateOptionsMenu(menu!!, menuInflater!!)
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
            listPages[tab.position].onCreateOptionsMenu(menu!!, menuInflater!!)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) { }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        this.menu = menu
        this.menuInflater = inflater

        tabLayout.addOnTabSelectedListener(tabselectedListener)
        tabLayout.getTabAt(tabLayout.selectedTabPosition)?.select()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return listPages[tabLayout.selectedTabPosition].onOptionsItemSelected(item)
    }
}