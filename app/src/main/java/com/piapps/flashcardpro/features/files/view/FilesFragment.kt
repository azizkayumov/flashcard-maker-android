package com.piapps.flashcardpro.features.files.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.toast
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.core.util.PermissionUtils
import com.piapps.flashcardpro.features.files.controller.FilesAdapter
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.alert
import uz.yuridik.client.features.files.presenter.FilesPresenter

/**
 * Created by abduaziz on 10/15/18 at 11:22 PM.
 */

class FilesFragment : BaseFragment(), FilesView, FilesAdapter.OnItemClickListener {

    override fun create() {
        enterAnimation = ENTER_FROM_RIGHT
        exitAnimation = EXIT_TO_RIGHT
        canSwipe = true
        super.create()
    }

    lateinit var recyclerView: RecyclerView
    lateinit var fab: FloatingActionButton
    lateinit var presenter: FilesPresenter

    var onFilesSelectedListener: OnFilesSelectedListener? = null
    private val adapter = FilesAdapter()

    override fun createView(context: Context): View? {
        return FilesFragmentUI().createView(AnkoContext.Companion.create(context, this))
    }

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        presenter = FilesPresenter(this)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        actionBar?.onBackClick {
            close()
        }

        fab.setOnClickListener {
            onFilesSelectedListener?.onFilesSelected(adapter.selectedFiles)
            close()
        }
    }

    override fun setTitle(title: Int) {
        actionBar?.setTitle(ctx.getLocalizedString(title))
    }

    override fun fabVisibility(visible: Boolean) {
        if (visible) {
            fab.show()
        } else {
            fab.hide()
        }
    }

    override fun resume() {
        askPermission()
    }

    override fun askPermission() {
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
        fetchFiles()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (PermissionUtils.permissionGranted(requestCode, grantResults, PermissionUtils.READ_EXTERNAL_STORAGE)
        ) {
            fetchFiles()
        } else {
            ctx.alert(R.string.give_permission_storage) {
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

    private fun fetchFiles() {
        recyclerView.adapter = adapter
        adapter.addItemClickListener(this)
        adapter.fetchFiles(Environment.getExternalStorageDirectory())
    }

    override fun showToast(message: Int) {
        toast(message)
    }
}