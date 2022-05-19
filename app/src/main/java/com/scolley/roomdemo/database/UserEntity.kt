package com.scolley.roomdemo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "user_list_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "user_name")
    var name: String = "",

    @ColumnInfo(name = "user_age")
    var age: Int = 0,

    ) {

    override fun toString(): String {
        return "id = $id, name = $name, age = $age"
    }
}

