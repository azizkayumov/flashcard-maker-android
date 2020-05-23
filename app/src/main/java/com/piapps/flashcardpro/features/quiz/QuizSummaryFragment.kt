package com.piapps.flashcardpro.features.quiz

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.kent.layouts.textColorResource
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.features.MainActivity

/**
 * Created by abduaziz on 2020-05-23 at 06:49.
 */

class QuizSummaryFragment : BaseFragment() {

    companion object {
        fun summaryWith(accuracy: Int, weakCount: Int): QuizSummaryFragment {
            val fragment = QuizSummaryFragment()
            fragment.arguments = Bundle().apply {
                putInt("accuracy", accuracy)
                putInt("weak", weakCount)
            }
            return fragment
        }
    }

    override fun create() {
        enterAnimation = ENTER_FADEIN
        exitAnimation = EXIT_FADEOUT
        withOnBackPressed = true
        super.create()
    }

    lateinit var tvAccuracy: TextView
    lateinit var tvCreateHint: TextView
    lateinit var tvYes: TextView
    lateinit var tvNo: TextView

    override fun createView(context: Context): View? {
        return UI()
    }

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        val accuracy = args?.getInt("accuracy", 0) ?: 0
        val weakCount = args?.getInt("weak", 0) ?: 0

        tvAccuracy.text = "$accuracy"
        tvAccuracy.textColorResource = when (accuracy) {
            in 0..40 -> R.color.colorBadGrade
            in 41..80 -> R.color.colorAverageGrade
            in 81..100 -> R.color.colorGoodGrade
            else -> R.color.colorPrimaryText
        }

        if (weakCount == 0) {
            tvCreateHint.visibility = View.GONE
            tvNo.visibility = View.GONE
        }

        tvNo.setOnClickListener {
            ignoreSummary()
        }

        tvYes.setOnClickListener {
            close()
            onCreateNewSetListener?.onCreateNewSetWithWeakCards()
        }
    }

    private fun ignoreSummary() {
        (activity as MainActivity).removeFragmentType(QuizFragment::class.java)
        (activity as MainActivity).removeFragmentType(QuizSummaryFragment::class.java)
    }

    override fun onBackPressed() {
        ignoreSummary()
    }

    var onCreateNewSetListener: OnCreateNewSetListener? = null

    interface OnCreateNewSetListener {
        fun onCreateNewSetWithWeakCards()
    }

}