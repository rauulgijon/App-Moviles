package conexion

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.Race

// DAO para la tabla "races_table"
@Dao
interface RaceDao {

    // Obtiene todas las carreras, ordenadas por ronda (1, 2, 3...)
    @Query("SELECT * FROM races_table ORDER BY round ASC")
    fun getAllRaces(): LiveData<List<Race>>

    // Obtiene UNA sola carrera por su ID. (Para la pantalla de Detalle)
    @Query("SELECT * FROM races_table WHERE id = :raceId")
    fun getRaceById(raceId: Int): LiveData<Race>

    // Query para MainActivity: coge la primera carrera que aún no ha pasado
    // "date('now')" obtiene la fecha actual.
    @Query("SELECT * FROM races_table WHERE date >= date('now') ORDER BY date ASC LIMIT 1")
    fun getNextRace(): LiveData<Race>

    // Inserta una lista de carreras
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(races: List<Race>)

    // Inserta una sola carrera (no la usamos, pero es buena práctica tenerla)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(race: Race)
}