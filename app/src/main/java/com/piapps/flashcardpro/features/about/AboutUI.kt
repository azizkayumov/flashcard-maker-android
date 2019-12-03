package com.piapps.flashcardpro.features.about

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.piapps.flashcardpro.BuildConfig
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.*
import org.jetbrains.anko.*

/**
 * Created by abduaziz on 2019-10-27 at 00:12.
 */

class AboutUI : AnkoComponent<AboutFragment> {

    override fun createView(ui: AnkoContext<AboutFragment>) = with(ui) {
        frameLayout {
            lparams(matchParent, matchParent)
            backgroundColorResource = owner.theme.white
            isClickable = true

            verticalLayout {
                lparams(matchParent, matchParent)
                backgroundColorResource = owner.theme.colorPrimary
                isClickable = true

                ui.owner.actionBar = actionBar {
                    layoutParams = FrameLayout.LayoutParams(matchParent, dip(56))
                    backgroundColorResource = owner.theme.colorPrimary
                    tvTitle.textColorResource = owner.theme.colorPrimaryText
                    ivControl.setIconColor(ctx, owner.theme.colorIconActive)
                    ivMenu.setIconColor(ctx, owner.theme.colorIconActive)

                    setTitle(ctx.getLocalizedString(R.string.about))
                    onBackClick {
                        ui.owner.close()
                    }
                }

                imageView {
                    layoutParams = LinearLayout.LayoutParams(dip(56), dip(56)).apply {
                        margin = dip(16)
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                    imageResource = R.mipmap.ic_launcher
                }

                textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                    textSize = 16f
                    text = owner.getString(R.string.app_name)
                    textColorResource = owner.theme.colorAccent
                }

                textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                    textColorResource = owner.theme.colorSecondaryText
                    textSize = 12F
                    text = BuildConfig.VERSION_NAME
                }

                textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                        gravity = Gravity.CENTER_HORIZONTAL
                        horizontalMargin = dip(56)
                    }
                    gravity = Gravity.CENTER_HORIZONTAL
                    text = owner.getString(R.string.app_short_info)
                    textColorResource = owner.theme.colorPrimaryText
                    textSize = 12F
                }

                view {
                    layoutParams = LinearLayout.LayoutParams(matchParent, 1).apply {
                        topMargin = dip(16)
                    }
                    backgroundResource = owner.theme.colorDivider
                }

                view {
                    layoutParams = LinearLayout.LayoutParams(matchParent, 1)
                    backgroundResource = owner.theme.colorDivider
                    visibility = View.GONE
                }

                linearLayout {
                    layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent).apply {
                        padding = dip(16)
                    }
                    appCompatImageView {
                        layoutParams = LinearLayout.LayoutParams(dip(24), dip(24)).apply {
                            gravity = Gravity.CENTER
                        }
                        imageResource = R.drawable.youtube
                    }
                    textView {
                        layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                            gravity = Gravity.CENTER_VERTICAL
                            leftMargin = dip(16)
                        }
                        text = owner.getString(R.string.youtube)
                        textColorResource = owner.theme.colorPrimaryText
                    }
                    setRippleEffect()
                    setOnClickListener {
                        ui.owner.openLink(ctx.getLocalizedString(R.string.youtube_channel))
                    }
                }

                view {
                    layoutParams = LinearLayout.LayoutParams(matchParent, 1)
                    backgroundResource = owner.theme.colorDivider
                }

                linearLayout {
                    layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent).apply {
                        padding = dip(16)
                    }
                    appCompatImageView {
                        layoutParams = LinearLayout.LayoutParams(dip(24), dip(24)).apply {
                            gravity = Gravity.CENTER
                        }
                        imageResource = R.drawable.playstore
                    }
                    textView {
                        layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                            gravity = Gravity.CENTER_VERTICAL
                            leftMargin = dip(16)
                        }
                        text = owner.getString(R.string.play_store)
                        textColorResource = owner.theme.colorPrimaryText
                    }
                    setRippleEffect()
                    setOnClickListener {
                        ui.owner.openLink(ctx.getLocalizedString(R.string.app_link))
                    }
                }

                view {
                    layoutParams = LinearLayout.LayoutParams(matchParent, 1)
                    backgroundResource = owner.theme.colorDivider
                }

                linearLayout {
                    layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent).apply {
                        padding = dip(16)
                    }
                    appCompatImageView {
                        layoutParams = LinearLayout.LayoutParams(dip(24), dip(24)).apply {
                            gravity = Gravity.CENTER
                        }
                        imageResource = R.drawable.ic_email
                        setIconColor(ctx, owner.theme.colorIconActive)
                    }
                    textView {
                        layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                            gravity = Gravity.CENTER_VERTICAL
                            leftMargin = dip(16)
                        }
                        text = owner.getString(R.string.email_us)
                        textColorResource = owner.theme.colorPrimaryText
                    }
                    setRippleEffect()
                    setOnClickListener {
                        ui.owner.openEmail(ctx.getLocalizedString(R.string.developer_email))
                    }
                }

                view {
                    layoutParams = LinearLayout.LayoutParams(matchParent, 1)
                    backgroundResource = owner.theme.colorDivider
                }
            }

            linearLayout {
                layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                    gravity = Gravity.BOTTOM or Gravity.CENTER
                    bottomMargin = dip(10)
                }

                textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                    text = owner.getString(R.string.copyright)
                    textColorResource = owner.theme.colorSecondaryText
                }
            }
        }
    }

}