package com.example.cryptoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptoapp.pojo.CoinPriceInfo

// Так как базу данных за нас реализует библиотека Room, мы делаем класс abstract
@Database(entities = [CoinPriceInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    // Внутри класса создаем Singleton
    companion object{
        private const val DB_NAME = "main_db"
        private val LOCK = Any()
        private var db : AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            // Этот метод должен быть синхронизирован
            synchronized(LOCK){

                // Оператор let - код блока выполнится только в том случае, если не равно null
                db?.let { return it }

                // переменная db - нуллабельная поэтому создаем отдельную переменную
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                db = instance
                return instance
            }
        }
    }

    abstract fun coinPriceInfoDao(): CoinPriceInfoDao

}