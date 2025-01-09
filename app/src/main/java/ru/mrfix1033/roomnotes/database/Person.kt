package ru.mrfix1033.roomnotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
class Person(
    @ColumnInfo val name: String,
    @ColumnInfo val surname: String,
    @ColumnInfo val phoneNumber: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}