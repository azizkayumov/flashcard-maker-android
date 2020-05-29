package com.piapps.flashcardpro.core.platform.theme

import com.piapps.flashcardpro.R

/**
 * Created by abduaziz on 2019-09-22 at 00:55.
 */

class Theme(
    var id: Long = THEME_CLASSIC,
    var title: Int = R.string.app_name,
    var isSelected: Boolean = false,

    var colorPrimary: Int = R.color.colorPrimary,
    var colorPrimaryDark: Int = R.color.colorPrimaryDark,
    var colorAccent: Int = R.color.colorAccent,

    var colorIconActive: Int = R.color.colorIconActive,
    var colorIconInactive: Int = R.color.colorIconActive,

    var colorPrimaryText: Int = R.color.colorPrimaryText,
    var colorSecondaryText: Int = R.color.colorSecondaryText,

    var colorDivider: Int = R.color.colorDivider,
    var colorDividerLight: Int = R.color.colorDividerLight,

    var white: Int = R.color.white,
    var black: Int = R.color.black,

    var colorBackground: Int = R.color.colorBackground
) {
    companion object {
        val THEME_CLASSIC = 0L
        val THEME_NIGHT = 1L

        fun getTheme(id: Long): Theme {
            return when (id) {
                THEME_NIGHT -> night()
                else -> classic()
            }
        }

        fun classic() = Theme()

        fun night() = Theme(THEME_NIGHT).apply {
            title = R.string.app_name
            colorPrimary = R.color.colorPrimary_NIGHT
            colorPrimaryDark = R.color.colorPrimaryDark_NIGHT
            colorAccent = R.color.colorAccent_NIGHT

            colorIconActive = R.color.colorIconActive_NIGHT
            colorIconInactive = R.color.colorIconInactive_NIGHT

            colorPrimaryText = R.color.colorPrimaryText_NIGHT
            colorSecondaryText = R.color.colorSecondaryText_NIGHT

            colorDivider = R.color.colorDivider_NIGHT
            colorDividerLight = R.color.colorDividerLight_NIGHT

            white = R.color.white_NIGHT
            black = R.color.black_NIGHT

            colorBackground = R.color.colorBackground_NIGHT
        }
    }

    fun isNight() = id == THEME_NIGHT
}