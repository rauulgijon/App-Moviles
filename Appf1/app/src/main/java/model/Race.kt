package model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "races_table")
data class Race(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val circuit: String,
    val date: String,
    val location: String,
    val imageUrl: String?, // URL de Google Drive/Imgur
    val winner: String?,
    val laps: Int?,
    val time: String?,
    val latitude: Double,
    val longitude: Double,
    val round: Int = 0,
    val country: String? = null
) : Serializable