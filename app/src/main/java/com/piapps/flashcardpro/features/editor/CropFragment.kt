package com.piapps.flashcardpro.features.editor

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.isseiaoki.simplecropview.CropImageView
import com.isseiaoki.simplecropview.callback.CropCallback
import com.isseiaoki.simplecropview.callback.LoadCallback
import com.piapps.flashcardpro.core.extension.toast
import com.piapps.flashcardpro.core.platform.BaseFragment
import java.io.File

/**
 * Created by abduaziz on 2019-11-17 at 00:56.
 */

class CropFragment : BaseFragment(), CropView {

    companion object {
        fun cropFile(path: String): CropFragment {
            return CropFragment().apply {
                arguments = Bundle().apply {
                    putString("path", path)
                }
            }
        }
    }

    override fun create() {
        enterAnimation = ENTER_FADEIN
        exitAnimation = EXIT_FADEOUT
        super.create()
    }

    lateinit var presenter: CropPresenter
    lateinit var cropView: CropImageView
    lateinit var ivLeft: AppCompatImageView
    lateinit var ivRight: AppCompatImageView
    lateinit var ivDone: AppCompatImageView

    override fun createView(context: Context) = UI()

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        presenter = CropPresenter(this)
        appComponent.inject(presenter)

        val path = args?.getString("path", "") ?: ""
        presenter.path = path
        if (path.isNotBlank()) startCrop(path)

        ivDone.setOnClickListener {
            cropImage()
        }

        ivLeft.setOnClickListener {
            cropView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D)
        }

        ivRight.setOnClickListener {
            cropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D)
        }
    }

    fun startCrop(path: String) {
        cropView.setDebug(false)
        cropView.load(Uri.fromFile(File(path)))
            .initialFrameRect(null)
            .useThumbnail(true)
            .execute(mLoadCallback)
    }

    fun cropImage() {
        cropView.crop(cropView.sourceUri).execute(mCropCallback)
    }

    private val mLoadCallback = object : LoadCallback {
        override fun onSuccess() {}
        override fun onError(e: Throwable) {}
    }

    private val mCropCallback = object : CropCallback {
        override fun onSuccess(cropped: Bitmap) {
            presenter.save(cropped)
        }

        override fun onError(e: Throwable) {}
    }

    override fun showCropImageFile(path: String) {
        onCropListener?.onImageCropped(path)
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

    var onCropListener: OnCropListener? = null

    interface OnCropListener {
        fun onImageCropped(croppedPath: String)
    }

}