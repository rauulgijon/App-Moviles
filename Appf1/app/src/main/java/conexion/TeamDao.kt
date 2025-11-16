package conexion

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.Team

// DAO para la tabla "teams_table"
@Dao
interface TeamDao {

    // Obtiene todos los equipos, ordenados por puntos de mayor a menor.
    @Query("SELECT * FROM teams_table ORDER BY points DESC")
    fun getAllTeams(): LiveData<List<Team>>

    // Inserta una lista de equipos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(teams: List<Team>)
}