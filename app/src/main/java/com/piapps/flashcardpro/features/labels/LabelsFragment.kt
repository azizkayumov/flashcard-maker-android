package com.piapps.flashcardpro.features.labels

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.db.tables.LabelDb
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.toast
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.features.MainActivity
import com.piapps.flashcardpro.features.editor.adapter.LabelsEditAdapter
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.alert

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

    override fun createView(context: Context): View? {
        return LabelsUI().createView(AnkoContext.Companion.create(context, this))
    }

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
        val label = LabelDb(System.currentTimeMillis(), s)
        presenter.checkSaveLabel(label)
    }

    override fun showLabel(label: LabelDb) {
        adapter.list.add(label)
        adapter.notifyDataSetChanged()
    }

    override fun onDeleteLabel(l: LabelDb) {
        ctx.alert {
            message = ctx.getLocalizedString(R.string.delete_this_label)
            positiveButton(ctx.getLocalizedString(R.string.yes)) {
                presenter.deleteLabelOffline(l)
                adapter.remove(l)
            }
            negativeButton(ctx.getLocalizedString(R.string.no)) {
                it.dismiss()
            }
        }.show()
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