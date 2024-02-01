package co.tiagoaguiar.fitnesstracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// Código padrão
@Database(entities = [Calc::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase()  {

    abstract fun calcDao(): CalcDao

    //Padrao singleton , instancia unica
    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            return if (INSTANCE == null) {
                //tenho que criar o banco
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "fitness_tracker"
                    ).build()
                }
                INSTANCE as AppDatabase
            } else {
                INSTANCE as AppDatabase
            }
//            if (INSTANCE == null) {
//                //tenho que criar o banco
//                synchronized(this) {
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        AppDatabase::class.java,
//                        "fitness_tracker"
//                    ).build()
//                }
//                return INSTANCE as AppDatabase
//            } else {
//                return INSTANCE as AppDatabase
//            }

        }
    }


}