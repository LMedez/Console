package com.luc.artistonprice

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.luc.common.entities.SettingsEntity
import com.luc.data.local.LocalDatabase
import com.luc.data.local.dao.SettingsDao
import com.luc.data.local.dao.insertOrUpdate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SettingsDaoTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var settingsDao: SettingsDao
    private lateinit var localDatabase: LocalDatabase

    @Before
    fun setUp() {
        localDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDatabase::class.java
        ).allowMainThreadQueries().build()

        settingsDao = localDatabase.settingsDao()
    }

    @Test
    fun insertOrUpdate() = runBlockingTest {
        val settingsObjects = listOf(
            SettingsEntity(applyGain = false, applyIva = true),
            SettingsEntity(applyGain = true, applyIva = false),
            SettingsEntity(applyGain = false, applyIva = false)
        )

        settingsObjects.forEach {
            settingsDao.insertOrUpdate(it)
        }

        val setting = settingsDao.getSettings().first()
        assertThat(setting).isEqualTo(settingsObjects.last())
    }

    @After
    fun tearDown() {
        localDatabase.close()
    }
}