package com.piapps.flashcard.ui.anim

import android.graphics.Camera
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation

/**
 * Created by abduaziz on 2/17/17.
 */

class FlipAnimation(private var fromView: View?, private var toView: View?, mAnimationDuration: Int, var forward: Boolean = true) : Animation() {

    private var camera: Camera? = null

    private var centerX: Float = 0.toFloat()
    private var centerY: Float = 0.toFloat()

    init {
        duration = mAnimationDuration.toLong()
        fillAfter = false
        interpolator = AccelerateDecelerateInterpolator()
    }

    fun reverse() {
        forward = false
        val switchView = toView
        toView = fromView
        fromView = switchView
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
        centerX = (width / 2).toFloat()
        centerY = (height / 2).toFloat()
        camera = Camera()
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        // Angle around the y-axis of the rotation at the given time
        // calculated both in radians and degrees.
        val radians = Math.PI * interpolatedTime
        var degrees = (180.0 * radians / Math.PI).toFloat()

        // Once we reach the midpoint in the animation, we need to hide the
        // source view and show the destination view. We also need to change
        // the angle by 180 degrees so that the destination does not come in
        // flipped around
        if (interpolatedTime >= 0.5f) {
            degrees -= 180f
            fromView!!.visibility = View.GONE
            toView!!.visibility = View.VISIBLE
        }

        if (forward)
            degrees = -degrees //determines direction of rotation when flip begins

        val matrix = t.matrix
        camera!!.save()
        camera!!.rotateY(degrees)
        camera!!.getMatrix(matrix)
        camera!!.restore()
        matrix.preTranslate(-centerX, -centerY)
        matrix.postTranslate(centerX, centerY)
    }
}