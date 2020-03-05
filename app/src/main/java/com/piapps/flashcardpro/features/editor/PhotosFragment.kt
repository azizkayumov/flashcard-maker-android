package com.piapps.flashcardpro.features.editor

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.*
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.core.util.FileUtils
import com.piapps.flashcardpro.core.util.PermissionUtils
import com.piapps.flashcardpro.features.MainActivity
import com.piapps.flashcardpro.features.editor.adapter.PhotosAdapter

/**
 * Created by abduaziz on 2019-10-14 at 22:40.
 */

class PhotosFragment : BaseFragment(), PhotosAdapter.OnImageSelectedListener, CropFragment.OnCropListener {

    override fun create() {
        enterAnimation = ENTER_FROM_RIGHT
        exitAnimation = EXIT_TO_RIGHT
        canSwipe = true
        super.create()
    }

    lateinit var recyclerView: RecyclerView
    lateinit var ivOk: AppCompatImageView
    var adapter = PhotosAdapter()

    override fun createView(context: Context) = UI()

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        actionBar?.onBackClick {
            close()
        }

        ivOk.setOnClickListener {
            (activity as MainActivity).openFragment(CropFragment.cropFile(adapter.selectedImage).apply {
                onCropListener = this@PhotosFragment
            }, true)
        }
    }

    override fun resume() {
        askPermission()
    }

    fun askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
                ctx,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            activity?.requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PermissionUtils.READ_EXTERNAL_STORAGE
            )
            return
        }
        fetchPhotos()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (PermissionUtils.permissionGranted(requestCode, grantResults, PermissionUtils.READ_EXTERNAL_STORAGE)) {
            fetchPhotos()
        } else {
            ctx.alert {
                setMessage(ctx.getLocalizedString(R.string.permission_error))
                positiveButton(ctx.getLocalizedString(R.string.yes)) {
                    PermissionUtils.openPermissionSettings(activity!!)
                    close()
                }
                negativeButton(ctx.getLocalizedString(R.string.no)) {
                    close()
                }
            }.show()
        }
    }

    fun fetchPhotos() {
        recyclerView.adapter = adapter
        adapter.onImageSelectedListener = this
        doAsync {
            val paths = FileUtils.fetchPaths(ctx, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            val images = arrayListOf<PhotosAdapter.Image>()
            paths.forEach {
                images.add(PhotosAdapter.Image(it))
            }
            adapter.list.addAll(images)
            uiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onImageSelected(path: String) {
        if (adapter.selectedImage.isNotBlank())
            ivOk.visibility = View.VISIBLE
        else
            ivOk.visibility = View.GONE
    }

    override fun onImageCropped(croppedPath: String) {
        onCardImageSelectedListener?.onCardBackgroundImageSelected(croppedPath)
        close()
    }

    var onCardImageSelectedListener: OnCardImageSelectedListener? = null

    interface OnCardImageSelectedListener {
        fun onCardBackgroundImageSelected(path: String)
    }
}