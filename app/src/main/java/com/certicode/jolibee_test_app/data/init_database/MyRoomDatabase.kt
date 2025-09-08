package com.nick.samplecomposewithhiltandroom.data.room_database.init_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.certicode.jolibee_test_app.data.jollibeedata.dao.BusinessDao
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.data.jollibeedata.dao.CategoryDao
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel
import com.certicode.jolibee_test_app.data.jollibeedata.dao.PeopleDao
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel
import com.certicode.jolibee_test_app.data.jollibeedata.dao.TagsDao
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TasKModel
import com.certicode.jolibee_test_app.data.jollibeedata.dao.TaskDao
import com.certicode.jolibee_test_app.data.type_converter.StringListConverter
import javax.inject.Inject



@Database(
    entities = [
        BusinessModel::class,
        CategoryModel::class,
        TasKModel::class,
        PeopleModel::class,
        TagsModel::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    StringListConverter::class,
)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun businessDao(): BusinessDao
    abstract fun peopleDao(): PeopleDao
    abstract fun categoryDao(): CategoryDao
    abstract fun taskDao(): TaskDao
    abstract fun tagsDao(): TagsDao

    @Inject
    internal lateinit var myRoomDatabase: MyRoomDatabase

    companion object {
        internal const val DB_NAME = "JOLLIBEE_DB"
    }

    fun deleteAll() {
        myRoomDatabase.clearAllTables()
    }
}
