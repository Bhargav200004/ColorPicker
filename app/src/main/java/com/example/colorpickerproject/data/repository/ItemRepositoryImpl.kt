package com.example.colorpickerproject.data.repository

import com.example.colorpickerproject.domain.model.Item
import com.example.colorpickerproject.data.local.ItemDao
import com.example.colorpickerproject.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemDao: ItemDao
) : ItemRepository {

    override fun getAllData(): Flow<List<Item>> {
        return itemDao.getAllItems()
    }

    override suspend fun insert(item: Item) {
        return itemDao.insertItem(item)
    }

}