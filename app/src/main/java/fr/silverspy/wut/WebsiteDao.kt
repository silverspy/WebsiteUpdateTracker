package fr.silverspy.wut

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WebsiteDao {

    @Query("SELECT * FROM websites")
    fun getAllWebsites(): List<Website>

    @Insert
    fun addWebsite(website: Website)

    @Update
    fun updateWebsite(website: Website)

    @Delete
    fun deleteWebsite(website: Website)
}
