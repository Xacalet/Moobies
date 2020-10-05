package com.xacalet.moobies.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xacalet.moobies.db.AppDatabase
import com.xacalet.moobies.db.model.UserRatingDbModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserRatingDaoTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var userRatingDao: UserRatingDao

    @Before
    @ExperimentalCoroutinesApi
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userRatingDao = appDatabase.userRatingDao()
    }

    @After
    fun teaseDown() {
        appDatabase.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getByRatingReturnsListOfIds() = runBlocking {
        val data = listOf(
            UserRatingDbModel(1L, 6),
            UserRatingDbModel(2L, 4),
            UserRatingDbModel(3L, 6)
        )
        data.forEach { rating -> userRatingDao.insert(rating) }

        val result = userRatingDao.getByRating(6)

        assertEquals(listOf(1L, 3L), result)
    }
}
