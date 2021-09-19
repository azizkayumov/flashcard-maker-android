package com.kent.layouts

import android.content.Context
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.google.android.material.card.MaterialCardView

/**
 * Created by abduaziz on 2020-02-23 at 06:07.
 */

/*
* Cards
* - Regular card view
* - Material card view
* */

inline fun Context.cardView(init: CardView.() -> Unit = {}): CardView {
    return CardView(this).apply(init)
}

inline fun ViewGroup.cardView(init: CardView.() -> Unit = {}): CardView {
    val c = CardView(context).apply(init)
    addView(c)
    return c
}

inline fun Context.materialCardView(init: MaterialCardView.() -> Unit = {}): MaterialCardView {
    return MaterialCardView(this).apply(init)
}

inline fun ViewGroup.materialCardView(init: MaterialCardView.() -> Unit = {}): MaterialCardView {
    val m = MaterialCardView(context).apply(init)
    addView(m)
    return m
}