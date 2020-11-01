package com.piapps.flashcardpro.features.main

import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.platform.BasePresenter
import com.piapps.flashcardpro.core.platform.theme.Theme
import com.piapps.flashcardpro.core.settings.Settings
import com.piapps.flashcardpro.features.main.interactor.*
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-09-25 at 22:18.
 */

class MainPresenter(var view: MainView?) : BasePresenter(view) {

    companion object {
        val ALL_SETS = 0
        val TRASH = 2
    }

    @Inject
    lateinit var getLabels: GetLabels

    @Inject
    lateinit var getAllSets: GetAllSets

    @Inject
    lateinit var getArchiveSets: GetArchiveSets

    @Inject
    lateinit var getTrashSets: GetTrashSets

    @Inject
    lateinit var getLabelSets: GetLabelSets

    @Inject
    lateinit var putBackSet: PutBackSet

    @Inject
    lateinit var emptyTrash: EmptyTrash

    @Inject
    lateinit var settings: Settings

    fun loadLabels() {
        getLabels {
            view?.showLabels(it)
        }
    }

    var currentNav = -1
    fun loadAllSets() {
        if (currentNav == 0) {
            validateSets(R.string.no_all_sets_found)
            return
        }
        currentNav = 0
        view?.setTitle(R.string.app_name)
        getAllSets {
            view?.showSets(it)
            validateSets(R.string.no_all_sets_found)
        }
    }

    fun loadArchiveSets() {
        if (currentNav == 1) {
            validateSets(R.string.no_archive_sets_found)
            return
        }
        currentNav = 1
        view?.setTitle(R.string.archive)
        getArchiveSets {
            view?.showSets(it)
            validateSets(R.string.no_archive_sets_found)
        }
    }

    fun loadTrashSets() {
        if (currentNav == 2) {
            validateSets(R.string.no_trash_sets_found)
            return
        }
        currentNav = 2
        view?.setTitle(R.string.trash)
        getTrashSets {
            view?.showSets(it)
            validateSets(R.string.no_trash_sets_found)
        }
    }

    fun loadLabelSets(label: String) {
        currentNav = 100
        view?.setTitle(label)
        getLabelSets(label) {
            view?.showSets(it)
            validateSets(R.string.no_label_sets_found)
        }
    }

    private fun validateSets(res: Int) {
        if (view?.setCount() == 0) {
            view?.showNothingFound(res)
        } else {
            view?.hideNothingFound()
        }
    }

    fun validateNothingFound() {
        when (currentNav) {
            0 -> validateSets(R.string.no_all_sets_found)
            1 -> validateSets(R.string.no_trash_sets_found)
            -1 -> view?.hideNothingFound()
            else -> validateSets(R.string.no_label_sets_found)
        }
    }

    fun putBack(setId: Long) {
        putBackSet(setId) {
            view?.onSetPutBack(it)
        }
    }

    fun clearTrash() {
        emptyTrash() {
            view?.showToast(R.string.trash_cleared)
            view?.removeSets()
        }
    }

    fun changeTheme(theme: Theme): Theme {
        if (theme.id == Theme.THEME_NIGHT) {
            settings.setTheme(Theme.THEME_CLASSIC)
            return Theme.classic()
        } else {
            settings.setTheme(Theme.THEME_NIGHT)
            return Theme.night()
        }
    }

}