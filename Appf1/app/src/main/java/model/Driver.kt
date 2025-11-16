package model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "drivers_table")
data class Driver(
    @PrimaryKey
    val driverId: String, // Ej: "HAM"
    val name: String,     // Ej: "Lewis Hamilton"
    val number: Int,      // Ej: 44
    val teamName: String, // Ej: "Mercedes"
    val nationality: String,
    val imageUrl: String?, // URL de la foto del piloto (de Drive)
    val points: Int = 0 // ✅ CAMPO AÑADIDO
) : Serializable