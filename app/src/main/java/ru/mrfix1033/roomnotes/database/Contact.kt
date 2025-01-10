package ru.mrfix1033.roomnotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
class Contact(
    @ColumnInfo val name: String,
    @ColumnInfo val surname: String,
    @ColumnInfo val phoneNumber: String,
    @ColumnInfo val timeStamp: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}