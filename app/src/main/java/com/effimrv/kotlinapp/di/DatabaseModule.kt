package com.effimrv.kotlinapp.di

import android.content.Context
import com.effimrv.kotlinapp.data.local.AppDatabase
import com.effimrv.kotlinapp.data.local.ItemDao
import com.effimrv.kotlinapp.data.repository.ItemRepository

object DatabaseModule {
    fun provideDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)

    // Helper para obtener el DAO directamente
    fun provideItemDao(context: Context): ItemDao = provideDatabase(context).itemDao()

    // Helper para obtener el Repository (usa el DAO)
    fun provideItemRepository(context: Context): ItemRepository = ItemRepository(provideItemDao(context))
}
