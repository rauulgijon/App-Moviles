package model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// Define la tabla "teams_table" para la base de datos
@Entity(tableName = "teams_table")
data class Team(
    @PrimaryKey // teamId es la Clave Primaria (ej: "mercedes")
    val teamId: String,
    val name: String,
    val logoUrl: String?, // URL del logo del equipo
    val carUrl: String?,  // URL de la foto del coche
    val points: Int = 0  // Puntos para la clasificación
) : Serializable