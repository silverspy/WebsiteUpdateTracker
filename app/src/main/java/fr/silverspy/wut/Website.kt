package fr.silverspy.wut

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "websites")
data class Website(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val url: String,
    val lastModified: Long
)

