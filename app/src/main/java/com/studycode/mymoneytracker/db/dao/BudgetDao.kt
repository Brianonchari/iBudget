package com.studycode.mymoneytracker.db.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.room.*
import com.studycode.mymoneytracker.db.models.Budget


@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: Budget)

    @Query("SELECT * FROM Budget")
    fun getBudgetList(): LiveData<List<Budget>>
    @Query("SELECT  SUM(amount) FROM Budget where strftime('%m', date('now'))")
    fun totalMonthlyBudget(): LiveData<Float>

    @Delete
    suspend fun deleteBudget(budget: Budget)
    @Update
     suspend fun updateBudget(budget: Budget)

    @Query("SELECT SUM(amount)  FROM Budget WHERE strftime('%Y', date('now'))")
    fun getTotalYearBudget(): LiveData<Float>

    //get budgets distribution by category

    @Query("SELECT amount FROM Budget WHERE category ='Food' and strftime('%m', date('now'))")
    fun getFoodBudget():LiveData<Float>

    @Query("SELECT amount FROM Budget WHERE category = 'Health' and strftime('%m', date('now'))")
    fun getHealthBudget():LiveData<Float>

    @Query("SELECT amount FROM Budget WHERE category= 'Rent' and strftime('%m', date('now'))")
    fun getRentBudget():LiveData<Float>

    @Query("SELECT amount FROM Budget WHERE category ='Education' and strftime('%m', date('now'))")
    fun getEducationBudget():LiveData<Float>

    @Query("SELECT amount FROM Budget WHERE category ='Transport' and strftime('%m', date('now'))")
    fun getTransportBudget():LiveData<Float>

    @Query("SELECT amount FROM Budget WHERE category ='Electricity' and strftime('%m', date('now'))")
    fun getElectricityBudget():LiveData<Float>

    @Query("SELECT amount FROM Budget WHERE category ='Fitness' and strftime('%m', date('now'))")
    fun getFitnessBudget():LiveData<Float>

    @Query("SELECT amount FROM Budget WHERE category ='Misc Payments' and strftime('%m', date('now'))")
    fun getMiscPaymentsBudget():LiveData<Float>

    @Query("SELECT amount FROM Budget WHERE category ='Water' and strftime('%m', date('now'))")
    fun getWaterBudget():LiveData<Float>

    @Query("SELECT amount FROM Budget WHERE category ='Other' and strftime('%m', date('now'))")
    fun getOtherBudget():LiveData<Float>

}