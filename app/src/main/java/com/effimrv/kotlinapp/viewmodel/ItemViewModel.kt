package com.effimrv.kotlinapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.effimrv.kotlinapp.data.local.AppDatabase
import com.effimrv.kotlinapp.data.model.Item
import com.effimrv.kotlinapp.data.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application.applicationContext)
    private val repo = ItemRepository(db.itemDao())

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadAll()
    }

    fun loadAll() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _items.value = repo.getAll()
            _loading.value = false
        }
    }

    fun insert(title: String, description: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insert(Item(title = title, description = description))
            _items.value = repo.getAll()
        }
    }

    fun delete(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.delete(item)
            _items.value = repo.getAll()
        }
    }

    // Nuevo: obtener item por id de forma suspendible
    suspend fun getByIdSuspend(id: Long): Item? {
        return repo.getById(id)
    }
}
