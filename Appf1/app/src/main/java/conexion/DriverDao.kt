package conexion

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.Driver

// @Dao = Data Access Object. Define las consultas SQL para una tabla.
@Dao
interface DriverDao {

    // @Query = una consulta SQL
    // "SELECT * FROM drivers_table" = Selecciona todas las columnas de la tabla de pilotos
    // "ORDER BY points DESC" = Ordénalas por la columna 'points' de mayor a menor.
    // LiveData<> = Hace que esta consulta sea "observable". La UI se actualizará sola
    //              si los datos de los pilotos cambian.
    @Query("SELECT * FROM drivers_table ORDER BY points DESC")
    fun getAllDrivers(): LiveData<List<Driver>>

    // @Insert = una consulta de inserción.
    // onConflict = Si intentamos insertar un piloto con una PrimaryKey (driverId) que
    //              ya existe, OnConflictStrategy.REPLACE le dice a Room que lo sobrescriba.
    // suspend = Le dice a Kotlin que esta función debe llamarse desde una Coroutine
    //           (porque acceder a la BD no puede hacerse en el hilo principal).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(drivers: List<Driver>)
}