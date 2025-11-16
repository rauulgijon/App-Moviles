package conexion

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.Driver

@Dao
interface DriverDao {
    @Query("SELECT * FROM drivers_table ORDER BY points DESC") // ✅ CAMBIADO
    fun getAllDrivers(): LiveData<List<Driver>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(drivers: List<Driver>)
}