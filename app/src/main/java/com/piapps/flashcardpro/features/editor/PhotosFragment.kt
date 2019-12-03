package com.piapps.flashcardpro.features.editor

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.core.util.FileUtils
import com.piapps.flashcardpro.core.util.PermissionUtils
import com.piapps.flashcardpro.features.MainActivity
import com.piapps.flashcardpro.features.editor.adapter.PhotosAdapter
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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

    override fun createView(context: Context): View? {
        return PhotosUI().createView(AnkoContext.create(context, this))
    }

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
            ctx.alert(R.string.permission_error) {
                positiveButton(R.string.yes) {
                    PermissionUtils.openPermissionSettings(activity!!)
                    close()
                }
                negativeButton(R.string.no) {
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