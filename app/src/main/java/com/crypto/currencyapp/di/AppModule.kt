package com.crypto.currencyapp.di

import android.content.Context
import androidx.room.Room
import com.crypto.currencyapp.data.local.AppDatabase
import com.crypto.currencyapp.data.local.CurrencyDao
import com.crypto.currencyapp.data.repository.CurrencyRepository
import com.crypto.currencyapp.data.repository.CurrencyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "currency_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(database: AppDatabase): CurrencyDao {
        return database.currencyDao()
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(dao: CurrencyDao): CurrencyRepository {
        return CurrencyRepositoryImpl(dao)
    }
}