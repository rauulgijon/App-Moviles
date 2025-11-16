package model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// Define la tabla "races_table" para la base de datos
@Entity(tableName = "races_table")
data class Race(
    @PrimaryKey(autoGenerate = true) // Room generará un ID automático (1, 2, 3...)
    val id: Int = 0,
    val name: String,
    val circuit: String,
    val date: String,
    val location: String,
    val imageUrl: String?, // URL de la imagen del circuito
    val winner: String?, // Será nulo si la carrera no ha sucedido
    val laps: Int?,
    val time: String?,    // Tiempo del ganador (nulo si no ha sucedido)
    val latitude: Double, // Para Google Maps
    val longitude: Double, // Para Google Maps
    val round: Int = 0,   // Número de carrera (ej: 1, 2, 3...)
    val country: String? = null
) : Serializable