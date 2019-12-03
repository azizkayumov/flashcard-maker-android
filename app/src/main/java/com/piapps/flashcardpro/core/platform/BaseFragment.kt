package com.piapps.flashcardpro.core.platform

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.piapps.flashcardpro.AndroidApplication
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.di.ApplicationComponent
import com.piapps.flashcardpro.core.platform.component.ActionBar
import com.piapps.flashcardpro.core.platform.component.menu.Menu
import com.piapps.flashcardpro.core.platform.component.menu.MenuItem
import com.piapps.flashcardpro.core.platform.theme.Theme
import com.piapps.flashcardpro.core.settings.Prefs
import org.jetbrains.anko.matchParent

abstract class BaseFragment() {

    companion object {
        val ENTER_FROM_RIGHT = 0
        val ENTER_FROM_LEFT = 1
        val ENTER_FROM_BOTTOM = 2
        val EXIT_TO_RIGHT = 3
        val EXIT_TO_LEFT = 4
        val EXIT_TO_BOTTOM = 5

        val ENTER_FADEIN = 6
        val EXIT_FADEOUT = 7
    }

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }

    // arguments
    var arguments: Bundle? = null

    // parent view: hosts both ghost view and fragment view
    internal lateinit var parentView: FrameLayout
    // ghost view for alpha animation
    internal var ghostView: View? = null
    // fragment view: to perform enter / exit animations
    internal var fragmentView: View? = null
    // action bar
    var actionBar: ActionBar? = null

    /*
    * position in a back stack
    * bigger means more on top of the activity's rootview
    * */
    //var backStackPosition = -1

    /*
    * swipe and removable
    * */
    protected var canSwipe = false

    fun canSwipeBack() = canSwipe

    /*
    * 'enter from' and 'exit to' animations
    * */
    var enterAnimation = -1
    var exitAnimation = -1

    // parent activity
    var activity: Activity? = null

    // context to add parent and ghost view
    lateinit var ctx: Context

    // theme: all colors can be accessed from this
    var theme: Theme = Theme() // classic theme by default

    // whether the child base fragments override back pressed
    var withOnBackPressed = false

    // alpha animate the ghost view
    internal fun ghostAlpha(alpha: Float) {
        ghostView?.alpha = alpha
    }

    // isPaused - true when another fragment is opened on top of it
    var isPaused = false

    // called when this is not added to the UI yet,
    // but going to create its answers immediately
    open fun create() {
        parentView = FrameLayout(ctx)
        parentView.layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
        if (canSwipe || exitAnimation >= 0 || enterAnimation >= 0) {
            ghostView = View(ctx)
            ghostView?.layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
            ghostView?.setBackgroundResource(R.drawable.ghost_drawable)
            parentView.addView(ghostView)
        }
        theme = Theme.getTheme(Prefs.get(ctx, Prefs.THEME_ID, Theme.THEME_CLASSIC))
    }

    // implemented by child classes
    abstract fun createView(context: Context): View?

    // called when successfully added to the stack
    open fun viewCreated(view: View?, args: Bundle?) {
        this.fragmentView = view
        // against unnecessary clicks from top fragment to bottom fragment
        this.fragmentView?.isClickable = true
        this.arguments = args
    }

    // creates menu for this fragment
    open fun createMenu(): Menu? = null

    open fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {}

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}

    // on menu item clicked
    open fun menuClicked(item: MenuItem) {}

    // called when parent activity resumed
    open fun resume() {
        isPaused = false
    }

    // called when paused (e.g. it is not removed, but another fragment is opened)
    open fun paused() {
        isPaused = true
    }

    // called when removed from the stack
    open fun removed() {}

    open fun close() {
        (activity as BaseActivity).removeTopFragment(exitAnimation >= 0)
    }

    open fun onBackPressed() {}
}