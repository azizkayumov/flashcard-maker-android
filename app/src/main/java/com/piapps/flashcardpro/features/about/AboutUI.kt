package com.piapps.flashcardpro.features.about

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.horizontalLayout
import com.kent.layouts.viewgroup.lparams
import com.kent.layouts.viewgroup.verticalLayout
import com.piapps.flashcardpro.BuildConfig
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.actionBar
import com.piapps.flashcardpro.core.extension.getLocalizedString

/**
 * Created by abduaziz on 2019-10-27 at 00:12.
 */

fun AboutFragment.UI(): View {
    return ctx.frameLayout {
        lparams(matchParent, matchParent)
        backgroundColorResource = theme.white
        isClickable = true

        verticalLayout {
            lparams(matchParent, matchParent)
            backgroundColorResource = theme.colorPrimary
            isClickable = true

            actionBar = actionBar {
                layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
                backgroundColorResource = theme.colorPrimary
                tvTitle.textColorResource = theme.colorPrimaryText
                ivControl.setIconColor(ctx, theme.colorIconActive)
                ivMenu.setIconColor(ctx, theme.colorIconActive)

                setTitle(ctx.getLocalizedString(R.string.about))
                onBackClick {
                    close()
                }
            }

            imageView {
                layoutParams = LinearLayout.LayoutParams(dip(56), dip(56)).apply {
                    margin = dip(16)
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                setImageResource(R.mipmap.ic_launcher)
            }

            textView {
                layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                textSize = 16f
                text = ctx.getLocalizedString(R.string.app_name)
                textColorResource = theme.colorAccent
            }

            textView {
                layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                textColorResource = theme.colorSecondaryText
                textSize = 12F
                text = BuildConfig.VERSION_NAME
            }

            textView {
                layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                    gravity = Gravity.CENTER_HORIZONTAL
                    leftMargin = dip(56)
                    rightMargin = dip(56)
                }
                gravity = Gravity.CENTER_HORIZONTAL
                text = ctx.getLocalizedString(R.string.app_short_info)
                textColorResource = theme.colorPrimaryText
                textSize = 12F
            }

            view {
                layoutParams = LinearLayout.LayoutParams(matchParent, 1).apply {
                    topMargin = dip(16)
                }
                backgroundColorResource = theme.colorDivider
            }

            view {
                layoutParams = LinearLayout.LayoutParams(matchParent, 1)
                backgroundColorResource = theme.colorDivider
                visibility = View.GONE
            }

            horizontalLayout {
                layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent).apply {
                    padding = dip(16)
                }
                imageView {
                    layoutParams = LinearLayout.LayoutParams(dip(24), dip(24)).apply {
                        gravity = Gravity.CENTER
                    }
                    setImageResource(R.drawable.youtube)
                }
                textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                        gravity = Gravity.CENTER_VERTICAL
                        leftMargin = dip(16)
                    }
                    text = ctx.getLocalizedString(R.string.youtube)
                    textColorResource = theme.colorPrimaryText
                }
                setRippleEffect()
                setOnClickListener {
                    openLink(ctx.getLocalizedString(R.string.youtube_channel))
                }
            }

            view {
                layoutParams = LinearLayout.LayoutParams(matchParent, 1)
                backgroundColorResource = theme.colorDivider
            }

            horizontalLayout {
                layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent).apply {
                    padding = dip(16)
                }
                imageView {
                    layoutParams = LinearLayout.LayoutParams(dip(24), dip(24)).apply {
                        gravity = Gravity.CENTER
                    }
                    setImageResource(R.drawable.playstore)
                }
                textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                        gravity = Gravity.CENTER_VERTICAL
                        leftMargin = dip(16)
                    }
                    text = ctx.getLocalizedString(R.string.play_store)
                    textColorResource = theme.colorPrimaryText
                }
                setRippleEffect()
                setOnClickListener {
                    openLink(ctx.getLocalizedString(R.string.app_link))
                }
            }

            view {
                layoutParams = LinearLayout.LayoutParams(matchParent, 1)
                backgroundColorResource = theme.colorDivider
            }

            horizontalLayout {
                layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent).apply {
                    padding = dip(16)
                }
                imageView {
                    layoutParams = LinearLayout.LayoutParams(dip(24), dip(24)).apply {
                        gravity = Gravity.CENTER
                    }
                    setImageResource(R.drawable.ic_email)
                    setIconColor(ctx, theme.colorIconActive)
                }
                textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                        gravity = Gravity.CENTER_VERTICAL
                        leftMargin = dip(16)
                    }
                    text = ctx.getLocalizedString(R.string.email_us)
                    textColorResource = theme.colorPrimaryText
                }
                setRippleEffect()
                setOnClickListener {
                    openEmail(ctx.getLocalizedString(R.string.developer_email))
                }
            }

            view {
                layoutParams = LinearLayout.LayoutParams(matchParent, 1)
                backgroundColorResource = theme.colorDivider
            }
        }

        horizontalLayout {
            layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                gravity = Gravity.BOTTOM or Gravity.CENTER
                bottomMargin = dip(10)
            }

            textView {
                layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                text = ctx.getLocalizedString(R.string.copyright)
                textColorResource = theme.colorSecondaryText
            }
        }
    }
}
