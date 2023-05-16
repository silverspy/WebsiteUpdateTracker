package fr.silverspy.wut

import android.content.Context

class WebsiteRepository(context: Context) {

    private val websiteDao: WebsiteDao

    init {
        val appDatabase = AppDatabase.getInstance(context)
        websiteDao = appDatabase.websiteDao()
    }

    fun getAllWebsites(): List<Website> {
        return websiteDao.getAllWebsites()
    }

    fun addWebsite(website: Website) {
        websiteDao.addWebsite(website)
    }

    fun updateWebsite(website: Website) {
        websiteDao.updateWebsite(website)
    }

    fun deleteWebsite(website: Website) {
        websiteDao.deleteWebsite(website)
    }
}
