package com.certicode.jolibee_test_app.testdata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessDao
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryDao
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleDao
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsDao
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TasKModel
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskDao

@Database(entities = [Quote::class, BusinessModel::class, PeopleModel::class, TagsModel::class, TasKModel::class, CategoryModel::class], version = 1, exportSchema = false)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun businessDao(): BusinessDao
    abstract fun categoryDao(): CategoryDao
    abstract fun peopleDao(): PeopleDao
    abstract fun tagsDao(): TagsDao
    abstract fun taskDao(): TaskDao


}