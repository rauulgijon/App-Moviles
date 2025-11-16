package model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// @Entity le dice a Room que esta clase es una "tabla" en la base de datos.
// tableName = "drivers_table" es el nombre de la tabla.
@Entity(tableName = "drivers_table")
data class Driver(
    @PrimaryKey // driverId es la Clave Primaria (única)
    val driverId: String, // Ej: "HAM"
    val name: String,     // Ej: "Lewis Hamilton"
    val number: Int,      // Ej: 44
    val teamName: String, // Ej: "Mercedes"
    val nationality: String,
    val imageUrl: String?, // URL de la foto del piloto
    val points: Int = 0   // Puntos para la clasificación
) : Serializable // Serializable permite pasar el objeto entre Activities (no lo usamos, pero es buena práctica)