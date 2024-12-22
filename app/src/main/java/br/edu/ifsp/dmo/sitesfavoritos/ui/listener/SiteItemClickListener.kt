package br.edu.ifsp.dmo.sitesfavoritos.ui.listener

interface SiteItemClickListener {
    fun clickSiteItem(position: Int)
    fun clickFavoriteSiteItem(position: Int)
    fun clickDeleteSiteItem(position: Int)
}