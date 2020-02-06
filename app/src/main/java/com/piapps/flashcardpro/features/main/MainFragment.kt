package com.piapps.flashcardpro.features.main

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.TextView
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.db.tables.SetDb
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.setIconColor
import com.piapps.flashcardpro.core.extension.toast
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.core.platform.component.menu.Menu
import com.piapps.flashcardpro.core.platform.component.menu.MenuItem
import com.piapps.flashcardpro.features.MainActivity
import com.piapps.flashcardpro.features.about.AboutFragment
import com.piapps.flashcardpro.features.editor.OnSetUpdatedListener
import com.piapps.flashcardpro.features.editor.SetFragment
import com.piapps.flashcardpro.features.main.MainPresenter.Companion.TRASH
import com.piapps.flashcardpro.features.main.adapter.MainAdapter
import com.piapps.flashcardpro.features.main.adapter.NavigationAdapter
import com.piapps.flashcardpro.features.main.entity.NavView
import com.piapps.flashcardpro.features.main.entity.SetView
import com.piapps.flashcardpro.features.settings.SettingsFragment
import org.jetbrains.anko.*
import kotlin.math.hypot

/**
 * Created by abduaziz on 2019-09-22 at 00:52.
 */

class MainFragment : BaseFragment(), MainView,
    NavigationAdapter.OnNavigationClickListener,
    MainAdapter.OnSetClickedListener,
    OnSetUpdatedListener {

    override fun create() {
        withOnBackPressed = true
        super.create()
    }

    lateinit var presenter: MainPresenter

    lateinit var navigationAdapter: NavigationAdapter
    lateinit var mainAdapter: MainAdapter

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var rvNavigation: RecyclerView
    lateinit var ivNightMode: AppCompatImageView
    lateinit var rv: RecyclerView
    lateinit var fab: FloatingActionButton
    lateinit var tvNothing: TextView

    override fun createView(context: Context): View {
        return MainUI().createView(AnkoContext.Companion.create(context, this))
    }

    override fun createMenu(): Menu? {
        return Menu().apply {
            addMenu(0, ctx.getLocalizedString(R.string.rate_us))
            addMenu(1, ctx.getLocalizedString(R.string.about))
        }
    }

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        presenter = MainPresenter(this)
        appComponent.inject(presenter)

        navigationAdapter = NavigationAdapter()
        createNavigation()
        rvNavigation.adapter = navigationAdapter
        navigationAdapter.onNavigationClickListener = this

        mainAdapter = MainAdapter()
        rv.adapter = mainAdapter
        mainAdapter.onSetClickedListener = this

        fab.setOnClickListener {
            if (presenter.currentNav == TRASH) {
                ctx.alert {
                    message = ctx.getLocalizedString(R.string.empty_trash)
                    positiveButton(ctx.getLocalizedString(R.string.yes)) {
                        presenter.clearTrash()
                        it.dismiss()
                    }
                    negativeButton(ctx.getLocalizedString(R.string.no)) {
                        it.dismiss()
                    }
                }.show()
                return@setOnClickListener
            }
            (activity as MainActivity).openFragment(SetFragment.newSet().apply {
                onSetUpdatedListener = this@MainFragment
            }, true)
        }

        ivNightMode.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toggleNightModeWithAnimation()
            } else {
                toggleNightMode()
            }
        }
    }

    override fun resume() {
        presenter.loadLabels()
        if (mainAdapter.itemCount == 0)
            presenter.loadAllSets()
    }

    fun toggleNightMode() {
        theme = presenter.changeTheme(theme)
        drawerLayout.backgroundColorResource = theme.colorBackground
        actionBar?.backgroundColorResource = theme.colorPrimary
        actionBar?.tvTitle?.textColorResource = theme.colorPrimaryText
        actionBar?.ivControl?.setIconColor(ctx, theme.colorIconActive)
        actionBar?.ivMenu?.setIconColor(ctx, theme.colorIconActive)
        ivNightMode.setIconColor(ctx, theme.colorIconActive)
        fab.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(ctx, theme.colorAccent))
        fab.rippleColor = ContextCompat.getColor(ctx, theme.colorDivider)
        fab.setIconColor(ctx, theme.white)
        navigationView.backgroundColorResource = theme.colorPrimary

        tvNothing.textColorResource = theme.colorSecondaryText

        navigationAdapter = NavigationAdapter(navigationAdapter.list)
        navigationAdapter.onNavigationClickListener = this
        rvNavigation.adapter = navigationAdapter

        (activity as MainActivity).changeStatusBarColor(theme.colorPrimaryDark)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun toggleNightModeWithAnimation() {
        val w = fragmentView!!.measuredWidth
        val h = fragmentView!!.measuredHeight

        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawerLayout!!.draw(canvas)

        (activity as MainActivity).showBitmapTemporarily(bitmap)

        val centerX = w - fragmentView!!.dip(56 + 28)
        val centerY = fragmentView!!.dip(56)
        val radius = hypot(w.toFloat(), h.toFloat())
        val anim =
            ViewAnimationUtils.createCircularReveal(fragmentView, centerX.toInt(), centerY.toInt(), 0F, radius)
        anim.duration = 500L
        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                toggleNightMode()
            }

            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                (activity as MainActivity).hideBitmap()
            }
        })
        anim.start()
    }

    fun createNavigation() {
        // ids are negative, not to collide with user-added labels
        navigationAdapter.add(NavView.header(-11))
        navigationAdapter.add(NavView.divider(-10))
        navigationAdapter.add(NavView.menu(-9, ctx.getLocalizedString(R.string.all), R.drawable.ic_all))
        ///navigationAdapter.add(NavView.menu(-7, ctx.getLocalizedString(R.string.recents), R.drawable.ic_recents))
        navigationAdapter.add(NavView.menu(-8, ctx.getLocalizedString(R.string.trash), R.drawable.ic_trash))
        // navigationAdapter.add(NavView.divider(-7))
        // navigationAdapter.add(NavView.menu(-6, ctx.getLocalizedString(R.string.settings), R.drawable.ic_settings))
        // navigationAdapter.add(NavView.menu(-5, ctx.getLocalizedString(R.string.edit_labels), R.drawable.ic_label))
        navigationAdapter.add(NavView.divider(-4))
        navigationAdapter.add(NavView.menuGroup(-3, ctx.getLocalizedString(R.string.labels)))
    }

    override fun onNavigationClick(nav: NavView) {
        if (nav.icon == R.drawable.ic_trash) {
            fab.imageResource = R.drawable.ic_empty_trash
        } else {
            fab.imageResource = R.drawable.ic_add
        }
        when (nav.icon) {
            R.drawable.ic_all -> {
                setTitle(ctx.getLocalizedString(R.string.app_name))
                presenter.loadAllSets()
            }
            R.drawable.ic_trash -> {
                setTitle(nav.title)
                presenter.loadTrashSets()
            }
            R.drawable.ic_settings -> {
                (activity as MainActivity).openFragment(SettingsFragment(), true)
            }
        }
        if (nav.isLabel()) {
            setTitle(nav.title)
            presenter.loadLabelSets(nav.title)
        }
        closeDrawer()
    }

    override fun menuClicked(item: MenuItem) {
        if (item.id == 0) {
            val appPackageName = activity!!.packageName
            try {
                activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
            } catch (anfe: android.content.ActivityNotFoundException) {
                activity?.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }
        if (item.id == 1) {
            (activity as MainActivity).openFragment(AboutFragment(), true)
        }
        super.menuClicked(item)
    }

    fun openDrawer() {
        drawerLayout.openDrawer(Gravity.START)
    }

    fun closeDrawer() {
        drawerLayout.closeDrawer(Gravity.START)
    }

    override fun setTitle(res: Int) {
        actionBar?.setTitle(ctx.getLocalizedString(res))
    }

    override fun setTitle(s: String) {
        actionBar?.setTitle(s)
    }

    override fun showLabels(list: List<NavView>) {
        navigationAdapter.showLabels(list)
    }

    override fun showSets(list: List<SetView>) {
        mainAdapter.clearSets()
        mainAdapter.addAll(list)
    }

    override fun onSetClicked(set: SetView) {
        if (set.isTrash) {
            ctx.alert {
                message = ctx.getLocalizedString(R.string.put_back_trash)
                positiveButton(ctx.getLocalizedString(R.string.yes)) {
                    presenter.putBack(set.id)
                    it.dismiss()
                }
                negativeButton(ctx.getLocalizedString(R.string.no)) {
                    it.dismiss()
                }
            }.show()
            return
        }
        val fragment = SetFragment.set(set.id)
        fragment.onSetUpdatedListener = this
        (activity as MainActivity).openFragment(fragment, true)
    }

    override fun onSetUpdated(set: SetDb) {
        mainAdapter.updateSet(set.toSetView())
        presenter.validateNothingFound()
    }

    override fun onSetMovedToTrash(set: SetDb) {
        mainAdapter.removeSet(set)
    }

    override fun onSetPutBack(set: SetDb) {
        mainAdapter.removeSet(set)
    }

    override fun removeSets() {
        mainAdapter.clearSets()
    }

    override fun setCount(): Int {
        return mainAdapter.itemCount
    }

    override fun showNothingFound(res: Int) {
        tvNothing.visibility = View.VISIBLE
        tvNothing.text = ctx.getLocalizedString(res)
    }

    override fun hideNothingFound() {
        tvNothing.visibility = View.GONE
    }

    override fun showToast(res: Int) {
        toast(res)
    }

    override fun removed() {
        presenter.clear()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            closeDrawer()
            return
        }
        (activity as MainActivity).onBackPressed()
    }

}