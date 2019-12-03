package com.piapps.flashcardpro.core.di

import com.piapps.flashcardpro.AndroidApplication
import com.piapps.flashcardpro.features.MainActivity
import com.piapps.flashcardpro.features.editor.CropPresenter
import com.piapps.flashcardpro.features.editor.DrawPresenter
import com.piapps.flashcardpro.features.editor.SetPresenter
import com.piapps.flashcardpro.features.labels.LabelsPresenter
import com.piapps.flashcardpro.features.main.MainPresenter
import com.piapps.flashcardpro.features.quiz.QuizPresenter
import com.piapps.flashcardpro.features.settings.SettingsPresenter
import com.piapps.flashcardpro.features.stats.StatsPresenter
import com.piapps.flashcardpro.features.study.StudyPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by abduaziz on 2019-09-22 at 00:06.
 */

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(activity: MainActivity)
    fun inject(presenter: MainPresenter)
    fun inject(presenter: SetPresenter)
    fun inject(presenter: LabelsPresenter)
    fun inject(presenter: SettingsPresenter)
    fun inject(presenter: StudyPresenter)
    fun inject(presenter: StatsPresenter)
    fun inject(presenter: QuizPresenter)
    fun inject(presenter: CropPresenter)
    fun inject(presenter: DrawPresenter)
}