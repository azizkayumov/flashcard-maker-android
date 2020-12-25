package com.piapps.flashcardpro

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.DatabaseService
import com.piapps.flashcardpro.core.db.tables.MyObjectBox
import com.piapps.flashcardpro.core.di.ApplicationComponent
import com.piapps.flashcardpro.core.di.ApplicationModule
import com.piapps.flashcardpro.core.settings.Settings
import io.objectbox.BoxStore
import io.objectbox.DebugFlags
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

/**
 * Created by abduaziz on 11/27/20 at 10:40 PM.
 */

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
open class AbstractDatabaseTest {

    protected val testDbDirectory = File("database-test/test-db")
    private var store: BoxStore? = null
    private lateinit var service: DatabaseService
    protected lateinit var repository: DatabaseRepository

    @Before
    fun setUp(){
        BoxStore.deleteAllFiles(testDbDirectory)
        store = MyObjectBox.builder()
            .directory(testDbDirectory)
            .debugFlags(DebugFlags.LOG_QUERIES or DebugFlags.LOG_QUERY_PARAMETERS)
            .build()
        service = DatabaseService(store!!)
        repository = DatabaseRepository.Database(service, Settings(ApplicationProvider.getApplicationContext()))
    }

    @Test
    fun isDatabaseValid(){
        assert(store != null)
    }

    @After
    fun cleanUp(){
        store?.close()
        store = null
        BoxStore.deleteAllFiles(testDbDirectory)
    }
}