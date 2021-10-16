package com.kent.layouts

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomappbar.BottomAppBar

/**
 * Created by abduaziz on 2020-02-23 at 05:43.
 */

inline fun Context.appBarLayout(init: AppBarLayout.() -> Unit): AppBarLayout {
    return AppBarLayout(this).apply(init)
}

inline fun ViewGroup.appBarLayout(init: AppBarLayout.() -> Unit): AppBarLayout {
    val a = AppBarLayout(context).apply(init)
    addView(a)
    return a
}

inline fun Context.collapsingToolbarLayout(init: CollapsingToolbarLayout.() -> Unit): CollapsingToolbarLayout {
    return CollapsingToolbarLayout(this).apply(init)
}

inline fun ViewGroup.collapsingToolbarLayout(init: CollapsingToolbarLayout.() -> Unit): CollapsingToolbarLayout {
    val a = CollapsingToolbarLayout(context).apply(init)
    addView(a)
    return a
}

inline fun Context.toolbar(init: Toolbar.() -> Unit): Toolbar {
    return Toolbar(this).apply(init)
}

inline fun ViewGroup.toolbar(init: Toolbar.() -> Unit): Toolbar {
    val a = Toolbar(context).apply(init)
    addView(a)
    return a
}

inline fun Context.materialToolbar(init: MaterialToolbar.() -> Unit): MaterialToolbar {
    return MaterialToolbar(this).apply(init)
}

inline fun ViewGroup.materialToolbar(init: MaterialToolbar.() -> Unit): MaterialToolbar {
    val a = MaterialToolbar(context).apply(init)
    addView(a)
    return a
}

inline fun Context.bottomAppBar(init: BottomAppBar.() -> Unit): BottomAppBar {
    return BottomAppBar(this).apply(init)
}

inline fun ViewGroup.bottomAppBar(init: BottomAppBar.() -> Unit): BottomAppBar {
    val b = BottomAppBar(context).apply(init)
    addView(b)
    return b
}