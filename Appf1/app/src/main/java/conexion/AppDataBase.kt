package conexion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import model.Driver // Importar Driver
import model.Race
import model.Team   // Importar Team

// ✅ AÑADIMOS Driver::class y Team::class y subimos la VERSIÓN a 2
@Database(entities = [Race::class, Driver::class, Team::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun raceDao(): RaceDao
    abstract fun driverDao(): DriverDao // ✅ AÑADIR
    abstract fun teamDao(): TeamDao     // ✅ AÑADIR

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "f1_database"
                )
                    // ✅ AÑADIR ESTO: Al cambiar la BBDD, borra la antigua
                    // Es necesario al subir de versión, o la app crasheará
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}