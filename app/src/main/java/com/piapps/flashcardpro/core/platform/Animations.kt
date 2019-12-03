package com.piapps.flashcardpro.core.platform

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import org.jetbrains.anko.dip

val SHORT_ANIMATION = 150L
val MID_ANIMATION = 200L
val LONG_ANIMATION = 300L
val MOTION_RANGE = 56

fun enterFromRight(fragmentView: View, backgroundView: View?): AnimatorSet {
    val animatorSet = AnimatorSet()
    animatorSet.playTogether(
        listOf(
            ObjectAnimator.ofFloat(fragmentView, "x", fragmentView.dip(MOTION_RANGE).toFloat(), 0F),
            ObjectAnimator.ofFloat(fragmentView, "alpha", 0F, 1F)
        )
    )
    backgroundView?.let {
        animatorSet.playTogether(ObjectAnimator.ofFloat(backgroundView, "alpha", 0F, 1F))
    }
    animatorSet.duration = SHORT_ANIMATION
    //animatorSet.interpolator = AccelerateDecelerateInterpolator()
    animatorSet.interpolator = DecelerateInterpolator()
    return animatorSet
}

fun exitToRight(
    width: Float,
    fragmentView: View?,
    backgroundView: View?,
    ghostView: View?,
    wasSwiping: Boolean
): AnimatorSet {
    val animatorSet = AnimatorSet()
    val currentX = fragmentView?.x ?: 0F
    val endX = if (wasSwiping) width else currentX + (fragmentView?.dip(MOTION_RANGE)?.toFloat()
        ?: width)

    fragmentView?.let {
        animatorSet.playTogether(ObjectAnimator.ofFloat(fragmentView, "x", currentX, endX))
        animatorSet.playTogether(ObjectAnimator.ofFloat(fragmentView, "alpha", 1F, 0F))
    }

    backgroundView?.let {
        animatorSet.playTogether(ObjectAnimator.ofFloat(backgroundView, "alpha", 1F, 0F))
    }

    ghostView?.let {
        if (wasSwiping) {
            animatorSet.playTogether(ObjectAnimator.ofFloat(ghostView, "alpha", ghostView.alpha, 0F))
        } else {
            ghostView.visibility = View.GONE
        }
    }

    animatorSet.duration = SHORT_ANIMATION
    animatorSet.interpolator = AccelerateInterpolator()
    return animatorSet
}

fun enterFromBottom(height: Float, fragmentView: View?, ghostView: View?): AnimatorSet {
    val animatorSet = AnimatorSet()

    fragmentView?.let {
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(
                fragmentView,
                "y",
                height,
                0F/*height - fragmentView.height.toFloat()*/
            )
        )
    }

    ghostView?.let {
        animatorSet.playTogether(ObjectAnimator.ofFloat(ghostView, "alpha", 0F, 1F))
    }

    animatorSet.duration = (LONG_ANIMATION + SHORT_ANIMATION) / 2
    animatorSet.interpolator = AccelerateDecelerateInterpolator()

    return animatorSet
}

fun exitToBottom(height: Float, fragmentView: View?, ghostView: View?): AnimatorSet {
    val animatorSet = AnimatorSet()

    fragmentView?.let {
        animatorSet.playTogether(ObjectAnimator.ofFloat(fragmentView, "y", fragmentView.y, height))
    }

    ghostView?.let {
        animatorSet.playTogether(ObjectAnimator.ofFloat(ghostView, "alpha", 1F, 0F))
    }

    animatorSet.duration = (LONG_ANIMATION + SHORT_ANIMATION) / 2
    animatorSet.interpolator = AccelerateInterpolator()
    return animatorSet
}

fun enterFadingIn(fragmentView: View?, ghostView: View?): AnimatorSet {
    val animatorSet = AnimatorSet()

    fragmentView?.let {
        animatorSet.playTogether(ObjectAnimator.ofFloat(fragmentView, "alpha", 0F, 1F))
    }

    ghostView?.let {
        animatorSet.playTogether(ObjectAnimator.ofFloat(ghostView, "alpha", 0F, 1F))
    }

    animatorSet.duration = SHORT_ANIMATION
    animatorSet.interpolator = AccelerateDecelerateInterpolator()
    return animatorSet
}

fun exitFadeOut(fragmentView: View?, ghostView: View?): AnimatorSet {
    val animatorSet = AnimatorSet()

    fragmentView?.let {
        animatorSet.playTogether(ObjectAnimator.ofFloat(fragmentView, "alpha", 1F, 0F))
    }

    ghostView?.let {
        animatorSet.playTogether(ObjectAnimator.ofFloat(ghostView, "alpha", 1F, 0F))
    }

    animatorSet.duration = SHORT_ANIMATION
    animatorSet.interpolator = AccelerateDecelerateInterpolator()
    return animatorSet
}



