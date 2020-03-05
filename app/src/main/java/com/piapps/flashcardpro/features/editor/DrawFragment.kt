package com.piapps.flashcardpro.features.editor

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.kent.layouts.dip
import com.kent.layouts.setIconColor
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.toast
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.features.MainActivity
import com.rm.freedrawview.FreeDrawView

/**
 * Created by abduaziz on 2019-11-17 at 02:51.
 */

class DrawFragment : BaseFragment(), DrawView, ColorFragment.OnDrawingColorSelectedListener {

    override fun create() {
        enterAnimation = ENTER_FROM_RIGHT
        exitAnimation = EXIT_TO_RIGHT
        super.create()
    }

    lateinit var presenter: DrawPresenter

    lateinit var ivUndo: AppCompatImageView
    lateinit var ivRedo: AppCompatImageView
    lateinit var ivDone: AppCompatImageView

    lateinit var ivPencil: AppCompatImageView
    lateinit var ivPen: AppCompatImageView
    lateinit var ivBrush: AppCompatImageView
    lateinit var ivColor: AppCompatImageView

    lateinit var drawView: FreeDrawView

    override fun createView(context: Context) = UI()

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        presenter = DrawPresenter(this)
        appComponent.inject(presenter)

        ivUndo.setOnClickListener {
            drawView.undoLast()
        }

        ivRedo.setOnClickListener {
            drawView.redoLast()
        }

        ivDone.setOnClickListener {
            saveDrawing()
        }

        ivPencil.setOnClickListener {
            ivPencil.setIconColor(ctx, theme.colorAccent)
            ivPen.setIconColor(ctx, theme.colorIconActive)
            ivBrush.setIconColor(ctx, theme.colorIconActive)
            drawView.setPaintWidthDp(ctx.dip(2).toFloat())
        }

        ivPen.setOnClickListener {
            ivPencil.setIconColor(ctx, theme.colorIconActive)
            ivPen.setIconColor(ctx, theme.colorAccent)
            ivBrush.setIconColor(ctx, theme.colorIconActive)
            drawView.setPaintWidthDp(ctx.dip(8).toFloat())
        }

        ivBrush.setOnClickListener {
            ivPencil.setIconColor(ctx, theme.colorIconActive)
            ivPen.setIconColor(ctx, theme.colorIconActive)
            ivBrush.setIconColor(ctx, theme.colorAccent)
            drawView.setPaintWidthDp(ctx.dip(16).toFloat())
        }

        ivColor.setOnClickListener {
            (activity as MainActivity).openFragment(ColorFragment.drawingColor().apply {
                onDrawingColorSelectedListener = this@DrawFragment
            }, true)
        }
    }

    fun saveDrawing() {
        drawView.getDrawScreenshot(object : FreeDrawView.DrawCreatorListener {
            override fun onDrawCreated(draw: Bitmap?) {
                if (draw != null)
                    presenter.save(draw)
                else
                    showToast(R.string.save_image_error)
            }

            override fun onDrawCreationError() {
                showToast(R.string.save_image_error)
            }
        })
    }

    override fun onDrawingColorSelected(color: Int) {
        ivColor.setIconColor(ctx, color)
        drawView.paintColor = ContextCompat.getColor(ctx, color)
    }

    override fun showDrawingImageFile(path: String) {
        onDrawingListener?.onDrawingImageSelected(path)
        close()
    }

    override fun exit() {
        close()
    }

    override fun showToast(res: Int) {
        toast(res)
    }

    override fun removed() {
        presenter.clear()
        super.removed()
    }

    var onDrawingListener: OnDrawingListener? = null

    interface OnDrawingListener {
        fun onDrawingImageSelected(path: String)
    }
}