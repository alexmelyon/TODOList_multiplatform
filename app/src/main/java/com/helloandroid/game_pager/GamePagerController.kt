package com.helloandroid.game_pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.helloandroid.App
import com.helloandroid.list_games.WORLD_KEY
import com.helloandroid.list_sessions.GAME_KEY

class GamePagerController(args: Bundle) : Controller(args) {

    private val world = App.instance.worlds.first { it.id == args.getInt(WORLD_KEY) }
    private val game = App.instance.games.first { it.id == args.getInt(GAME_KEY) && it.worldGroup == world.id }
//
    constructor(worldId: Int, gameId: Int) : this(Bundle().apply {
        putInt(WORLD_KEY, worldId)
        putInt(GAME_KEY, gameId)
    })
//
//    lateinit var tabLayout: TabLayout
//    lateinit var viewPager: ViewPager
//    val pagerAdapter: RouterPagerAdapter
//    val listCharactersController = ListCharactersController(world.id, game.id)
//    val listPages = listOf(
//        "Characters" to ListCharactersController(world.id, game.id),
//        "Sessions" to ListSessionsController(world.id, game.id)
//            .apply { delegate = listCharactersController })
//    lateinit var menu: Menu
//    lateinit var menuInflater: MenuInflater
//
//    init {
//        pagerAdapter = object : RouterPagerAdapter(this) {
//            override fun configureRouter(router: Router, position: Int) {
//                if (!router.hasRootController()) {
//                    val page = listPages[position].second
//                    router.setRoot(RouterTransaction.with(page))
//                }
//            }
//
//            override fun getCount(): Int {
//                return 2
//            }
//
//            override fun getPageTitle(position: Int): CharSequence? {
//                return listPages[position].first
//            }
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return View(container.context)
//        (activity as MainActivity).supportActionBar!!.title = game.name
//        setHasOptionsMenu(true)
//
//        val view = container.context.linearLayout {
//            orientation = LinearLayout.VERTICAL
//            this@GamePagerController.tabLayout = tabLayout {
//                minimumHeight = R.attr.actionBarSize
//                setTabTextColors(Color.BLACK, Color.BLACK)
//            }.lparams(matchParent, wrapContent)
//            this@GamePagerController.viewPager = viewPager {
//                adapter = pagerAdapter
//            }.lparams(matchParent, matchParent)
//        }
//        tabLayout.setupWithViewPager(viewPager)
//        return view
    }

//    override fun onDestroyView(view: View) {
//        if (activity?.isChangingConfigurations() ?: false) {
//            viewPager.adapter = null
//        }
//        tabLayout.setupWithViewPager(null)
//        super.onDestroyView(view)
//    }
//
//    val tabselectedListener = object : TabLayout.OnTabSelectedListener {
//        override fun onTabSelected(tab: TabLayout.Tab) {
//            listPages[tab.position].second.onCreateOptionsMenu(menu, menuInflater)
//        }
//
//        override fun onTabReselected(tab: TabLayout.Tab) {
//            listPages[tab.position].second.onCreateOptionsMenu(menu, menuInflater)
//        }
//
//        override fun onTabUnselected(tab: TabLayout.Tab?) { }
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        this.menu = menu
//        this.menuInflater = inflater
//
//        tabLayout.addOnTabSelectedListener(tabselectedListener)
//        tabLayout.getTabAt(tabLayout.selectedTabPosition)?.select()
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return listPages[tabLayout.selectedTabPosition].second.onOptionsItemSelected(item)
//    }
}