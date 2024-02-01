package co.tiagoaguiar.fitnesstracker.model

import androidx.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    //Buscar o objeto no banco
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong != null) Date(dateLong) else null
    }

    @TypeConverter
    //Gravar o objeto no banco
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}