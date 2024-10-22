package com.example.colorpickerproject.domain.repository

import com.example.colorpickerproject.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository  {

    fun getAllData() : Flow<List<Item>>

    suspend fun insert(item: Item)
}