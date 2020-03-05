package com.piapps.flashcardpro.features.labels

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.db.tables.LabelDb
import com.piapps.flashcardpro.core.extension.alert
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.positiveButton
import com.piapps.flashcardpro.core.extension.toast
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.features.MainActivity

/**
 * Created by abduaziz on 2019-10-26 at 21:00.
 */

class LabelsFragment : BaseFragment(), LabelsView, AddLabelFragment.OnLabelAddedListener,
    LabelsEditAdapter.OnDeleteLabelListener {

    companion object {
        fun forLabels(labels: String): LabelsFragment {
            return LabelsFragment().apply {
                arguments = Bundle().apply {
                    putString("labels", labels)
                }
            }
        }
    }

    override fun create() {
        enterAnimation = ENTER_FROM_RIGHT
        exitAnimation = EXIT_TO_RIGHT
        canSwipe = true
        super.create()
    }

    lateinit var presenter: LabelsPresenter

    lateinit var recyclerView: RecyclerView
    lateinit var ivOk: AppCompatImageView
    lateinit var fab: FloatingActionButton

    var adapter = LabelsEditAdapter(arrayListOf())

    override fun createView(context: Context) = UI()

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        presenter = LabelsPresenter(this)
        appComponent.inject(presenter)

        adapter.labels = args?.getString("labels", "") ?: ""
        adapter.onDeleteLabelListener = this

        presenter.loadLabels()

        ivOk.setOnClickListener {
            onLabelsEditListener?.onLabelsEdited(adapter.labels())
            close()
        }

        fab.setOnClickListener {
            (activity as MainActivity).openFragment(AddLabelFragment().apply {
                onLabelAddedListener = this@LabelsFragment
            }, true)
        }
    }

    override fun showLabels(list: List<LabelDb>) {
        adapter.list.clear()
        adapter.list.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun onEditName(s: String) {
        if (s.isBlank()) return
        val label = LabelDb(System.currentTimeMillis(), s)
        presenter.checkSaveLabel(label)
    }

    override fun showLabel(label: LabelDb) {
        adapter.list.add(label)
        adapter.notifyDataSetChanged()
    }

    override fun onDeleteLabel(l: LabelDb) {
        val dialog = ctx.alert {
            setMessage(ctx.getLocalizedString(R.string.delete_this_label))
            positiveButton(ctx.getLocalizedString(R.string.yes)) {
                presenter.deleteLabelOffline(l)
                adapter.remove(l)
            }
        }
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, ctx.getLocalizedString(R.string.no)) { d, i ->
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun showToast(res: Int) {
        toast(res)
    }

    override fun removed() {
        onLabelsEditListener?.onLabelsEdited(adapter.labels())
        presenter.clear()
        super.removed()
    }

    var onLabelsEditListener: OnLabelsEditListener? = null

    interface OnLabelsEditListener {
        fun onLabelsEdited(labels: String)
    }
}