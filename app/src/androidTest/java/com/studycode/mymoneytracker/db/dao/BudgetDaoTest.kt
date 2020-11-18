package com.studycode.mymoneytracker.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.studycode.mymoneytracker.db.AppDatabase
import com.studycode.mymoneytracker.db.models.Budget
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
class BudgetDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: AppDatabase
    private lateinit var  budgetDao: BudgetDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        budgetDao = database.getBudgetDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun inserBudget() = runBlockingTest {
        val budget =Budget("Food",100f, 100f, 100f, id=1)
        budgetDao.insertBudget(budget)
        val observeAllBudgets =budgetDao.getBudgetList().getOrAwaitValue()
        assertThat(observeAllBudgets).contains(budget)
    }

    @Test
    fun deleteBudget() = runBlockingTest {
        val budget = Budget("Food",100f, 100f, 100f,id=1)
        budgetDao.insertBudget(budget)
        budgetDao.deleteBudget(budget)
        val observeAllBudgets = budgetDao.getBudgetList().getOrAwaitValue()
        assertThat(observeAllBudgets).doesNotContain(budget)
    }

    @Test
    fun getTotaMonthlyBudget() = runBlockingTest {
        val budget1 = Budget("Food", 100f, 100f, 100f,id = 1 )
        val budget2 = Budget("Food", 100f, 100f, 100f , id = 2)
        val budget3 = Budget("Food", 100f, 100f, 100f , id = 3)
        budgetDao.insertBudget(budget1)
        budgetDao.insertBudget(budget2)
        budgetDao.insertBudget(budget3)
        val observeBudgetList = budgetDao.totalMonthlyBudget().getOrAwaitValue()
        assertThat(observeBudgetList).isEqualTo(100f + 100f + 100f)
    }
}