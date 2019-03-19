package com.helloandroid.game_pager

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

val SELECTED_TAB = "SELECTED_TAB"

class GamePagerController(args: Bundle) : Controller(args) {

    val PAGE_CHARACTERS = 0
    val PAGE_SESSIONS = 1

    val world = App.instance.worlds.first { it.id == args.getInt(WORLD_KEY) }
    val game = App.instance.games.first { it.id == args.getInt(GAME_KEY) && it.worldGroup == world.id }
    var selectedTab = 0

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
                selectedTab = position
                if (!router.hasRootController()) {
                    val page = when (position) {
                        PAGE_CHARACTERS -> ListCharactersController(world.id, game.id)
                        PAGE_SESSIONS -> ListSessionsController(world.id, game.id)
                        else -> throw IllegalArgumentException("Wrong page $position")
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
}