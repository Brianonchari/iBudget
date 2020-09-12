package com.studycode.mymoneytracker.di

import android.content.Context
import androidx.room.Room
import com.studycode.mymoneytracker.db.AppDatabase
import com.studycode.mymoneytracker.utils.Constants.APP_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        APP_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideIncomeDao(db:AppDatabase) = db.getIncomeDao()

    @Singleton
    @Provides
    fun providesBudgetDao(db:AppDatabase) = db.getBudgetDao()

    @Singleton
    @Provides
    fun providesExpenseDao(db:AppDatabase) = db.getExpenseDao()

    @Singleton
    @Provides
    fun providesFundDao(db: AppDatabase) = db.getFundsDao()
    
    @Singleton
    @Provides
    fun providesDebtDao(db:AppDatabase) = db.myDebtsDao()


    @Singleton
    @Provides
    fun providesTrasactionsDao(db: AppDatabase) = db.getTransactionsDao()
}