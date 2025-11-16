package conexion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import model.Driver // Importar Driver
import model.Race
import model.Team   // Importar Team

// La anotación @Database le dice a Room que esto es una base de datos.
// entities = lista de todas las "tablas" (nuestros data class) que queremos guardar.
// version = 3. IMPORTANTE: Si cambias la estructura de una tabla (ej: añades 'points')
//             DEBES subir este número (ej: de 2 a 3) o la app fallará.
@Database(entities = [Race::class, Driver::class, Team::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Funciones abstractas para que Room nos de acceso a los DAOs (consultas)
    abstract fun raceDao(): RaceDao
    abstract fun driverDao(): DriverDao
    abstract fun teamDao(): TeamDao

    // 'companion object' es como un 'static' en Java.
    // Permite tener una ÚNICA instancia de la base de datos para toda la app (Patrón Singleton).
    companion object {
        @Volatile // Asegura que 'INSTANCE' sea siempre la más actual
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Si INSTANCE no es nula, la devolvemos.
            // Si es nula, entramos al bloque 'synchronized' para crearla.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "f1_database" // Nombre del archivo de la base de datos
                )
                    // .fallbackToDestructiveMigration()
                    // Esto le dice a Room que si la 'version' sube,
                    // simplemente borre la base de datos antigua y cree una nueva.
                    // Esto es fácil para desarrollo, pero en producción se haría una "migración".
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // Devolvemos la instancia recién creada
                instance
            }
        }
    }
}