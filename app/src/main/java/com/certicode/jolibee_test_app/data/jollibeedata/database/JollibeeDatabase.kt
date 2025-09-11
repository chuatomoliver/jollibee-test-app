package com.certicode.jolibee_test_app.data.jollibeedata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessDao
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryDao
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleDao
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel
import com.certicode.jolibee_test_app.data.jollibeedata.quote.Quote
import com.certicode.jolibee_test_app.data.jollibeedata.quote.QuoteDao
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsDao
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel

import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskDao
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskModel

@Database(entities = [Quote::class, BusinessModel::class, PeopleModel::class, TagsModel::class, TaskModel::class, CategoryModel::class], version = 1, exportSchema = false)
abstract class JollibeeDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun businessDao(): BusinessDao
    abstract fun categoryDao(): CategoryDao
    abstract fun peopleDao(): PeopleDao
    abstract fun tagsDao(): TagsDao
    abstract fun taskDao(): TaskDao

}