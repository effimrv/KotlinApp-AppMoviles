package com.effimrv.kotlinapp.data.repository

import com.effimrv.kotlinapp.data.local.ItemDao
import com.effimrv.kotlinapp.data.model.Item

class ItemRepository(private val dao: ItemDao) {
    suspend fun getAll(): List<Item> = dao.getAll()
    suspend fun getById(id: Long): Item? = dao.getById(id)
    suspend fun insert(item: Item): Long = dao.insert(item)
    suspend fun update(item: Item) = dao.update(item)
    suspend fun delete(item: Item) = dao.delete(item)
}

