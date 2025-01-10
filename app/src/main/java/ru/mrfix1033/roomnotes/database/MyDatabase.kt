package ru.mrfix1033.roomnotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getContactDao(): ContactDao

    companion object {
        private var INSTANCE: MyDatabase? = null
        fun getInstance(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                // не могли бы объяснить, зачем создавать временную переменную?
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}