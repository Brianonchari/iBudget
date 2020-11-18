package com.studycode.mymoneytracker.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.studycode.mymoneytracker.db.AppDatabase
import com.studycode.mymoneytracker.db.models.Income
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class IncomeDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: AppDatabase
    private lateinit var  incomeDao: IncomeDao
    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        incomeDao = database.getIncomeDao()
    }
    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertIncome() = runBlockingTest {
        val income = Income("salary",10000f ,id=1)
        incomeDao.insertIncome(income)
        val allIncomeSources =incomeDao.getAllIncome().getOrAwaitValue()
        assertThat(allIncomeSources).contains(income)
    }

    @Test
    fun deleteIncome() = runBlockingTest {
        val income = Income("salary", 1000f, id=1)
        incomeDao.insertIncome(income)
        incomeDao.deleteIncome(income)
        val observeSourcesOfIncome = incomeDao.getAllIncome().getOrAwaitValue()
        assertThat(observeSourcesOfIncome).doesNotContain(income)
    }

    @Test
    fun getTotalIncome() = runBlockingTest {
        val income1 = Income("salary", 100000f, id=1)
        val income2 = Income("salary", 200000f, id=2)
        incomeDao.insertIncome(income1)
        incomeDao.insertIncome(income2)
        val totalIncome = incomeDao.getTotalMonthlyIncome().getOrAwaitValue()
        assertThat(totalIncome).isEqualTo(100000f + 200000f)
    }
}