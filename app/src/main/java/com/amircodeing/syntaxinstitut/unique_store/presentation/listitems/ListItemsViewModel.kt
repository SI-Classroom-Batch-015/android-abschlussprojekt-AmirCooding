package com.amircodeing.syntaxinstitut.unique_store.presentation.listitems

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import kotlinx.coroutines.launch

class ListItemsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService , AppDatabase.getAppDatabase(application))
    val categoryE = repository.categoryElectronic
    val categoryMen = repository.categoryMens
    val categoryWomen = repository.categoryWomen
    val categoryJewelery = repository.categoryJeweler



}