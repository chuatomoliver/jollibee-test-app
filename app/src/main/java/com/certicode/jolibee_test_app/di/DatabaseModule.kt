package com.certicode.jolibee_test_app.di

import android.content.Context
import androidx.room.Room
import com.certicode.jolibee_test_app.R
import com.certicode.jolibee_test_app.testdata.database.QuoteDao
import com.certicode.jolibee_test_app.testdata.database.QuoteDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): QuoteDatabase {
        return Room.databaseBuilder(
            context,
            QuoteDatabase::class.java,
            name = context.getString(R.string.app_database)
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideQuoteDao(database: QuoteDatabase): QuoteDao {
        return database.quoteDao()
    }
}