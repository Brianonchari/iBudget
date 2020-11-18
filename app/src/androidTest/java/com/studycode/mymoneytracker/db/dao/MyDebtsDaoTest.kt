package com.studycode.mymoneytracker.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.studycode.mymoneytracker.db.AppDatabase
import com.studycode.mymoneytracker.db.models.MyDebts
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
class MyDebtsDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: AppDatabase
    private lateinit var myDebtsDao: MyDebtsDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        myDebtsDao = database.myDebtsDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun saveDebt() = runBlockingTest {
        val debt = MyDebts("payee", 100f, "due",id = 1)
        myDebtsDao.insertDebt(debt)
        val observeDebts = myDebtsDao.getMyDebts().getOrAwaitValue()
        assertThat(observeDebts).contains(debt)
    }

    @Test
    fun deleteDebt() = runBlockingTest {
        val debt = MyDebts("hhd", 100f, "uwgc",id = 1)
        myDebtsDao.insertDebt(debt)
        myDebtsDao.deleteDebt(debt)
        val observeAlDebts=myDebtsDao.getMyDebts().getOrAwaitValue()
        assertThat(observeAlDebts).doesNotContain(debt)
    }

    @Test
     fun totalDebt() = runBlockingTest {
        val debt1 = MyDebts("payee", 100f, "due",id = 1)
        val debt2= MyDebts("payee", 100f, "due",id=2)
        val debt3= MyDebts("payee", 100f, "due",id = 3)
        myDebtsDao.insertDebt(debt1)
        myDebtsDao.insertDebt(debt2)
        myDebtsDao.insertDebt(debt3)
        val observeDebtList = myDebtsDao.totalMonthlyDebts().getOrAwaitValue()
        assertThat(observeDebtList).isEqualTo(100f+100f+100f)
    }
}