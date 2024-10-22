package com.example.colorpickerproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.colorpickerproject.domain.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM item")
    fun getAllItems(): Flow<List<Item>>

    @Insert
    suspend fun insertItem(item: Item)

}