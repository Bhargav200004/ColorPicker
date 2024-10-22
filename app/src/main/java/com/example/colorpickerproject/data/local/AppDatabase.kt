package com.example.colorpickerproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.colorpickerproject.domain.model.Item


@Database(entities = [Item::class] , version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao() : ItemDao
}