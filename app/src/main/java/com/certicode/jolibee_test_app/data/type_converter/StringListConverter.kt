package com.certicode.jolibee_test_app.data.type_converter

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun fromString(string: String): List<String> {
        return string.split(",").map { it.trim() }
    }
}