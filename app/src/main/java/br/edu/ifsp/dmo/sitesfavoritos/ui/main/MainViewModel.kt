package br.edu.ifsp.dmo.sitesfavoritos.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo.sitesfavoritos.model.entity.Site

class MainViewModel : ViewModel() {
    private val _sites = MutableLiveData<MutableList<Site>>(mutableListOf())
    val sites: LiveData<MutableList<Site>> get() = _sites

    fun addSite(site: Site) {
        val currentList = _sites.value ?: mutableListOf()
        currentList.add(site)
        _sites.value = currentList
    }

    fun removeSite(position: Int) {
        val currentList = _sites.value
        if (currentList != null && position in currentList.indices) {
            currentList.removeAt(position)
            _sites.value = currentList
        }
    }

    fun toggleFavorite(position: Int) {
        val currentList = _sites.value
        if (currentList != null && position in currentList.indices) {
            val site = currentList[position]
            site.favorito = !site.favorito
            _sites.value = currentList
        }
    }
}