package com.certicode.jolibee_test_app.data.jollibeedata.quote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "quote")
    val quote: String,
)