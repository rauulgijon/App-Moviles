package model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "teams_table")
data class Team(
    @PrimaryKey
    val teamId: String, // Ej: "mercedes"
    val name: String,   // Ej: "Mercedes-AMG PETRONAS"
    val logoUrl: String?, // URL del logo del equipo (de Drive)
    val carUrl: String?,   // URL de la foto del coche (de Drive)
    val points: Int = 0 // ✅ CAMPO AÑADIDO
) : Serializable