package com.certicode.jolibee_test_app.di

import android.content.Context
import androidx.room.Room
import com.certicode.jolibee_test_app.R
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessDao
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryDao
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleDao
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsDao
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskDao
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

    @Provides
    fun provideBusinessDao(database: QuoteDatabase): BusinessDao {
        return database.businessDao()
    }

    @Provides
    fun providePeopleDao(database: QuoteDatabase): PeopleDao {
        return database.peopleDao()
    }

    @Provides
    fun provideCategoryDao(database: QuoteDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    fun provideTaskDao(database: QuoteDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    fun provideTagsDao(database: QuoteDatabase): TagsDao {
        return database.tagsDao()
    }
}