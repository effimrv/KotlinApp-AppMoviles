package com.effimrv.kotlinapp.di

import android.content.Context
import com.effimrv.kotlinapp.data.local.AppDatabase

object DatabaseModule {
    fun provideDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)
}

