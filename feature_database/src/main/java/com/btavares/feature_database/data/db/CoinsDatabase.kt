package com.btavares.feature_database.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.btavares.feature_database.data.dao.UserDao
import com.btavares.feature_database.data.entities.*


@Database(entities = [User::class, NativeCurrency::class, Cryptocurrency::class, PortfolioBalance::class, PortfolioCurrency::class, UserCryptocurrency::class, UserNativeCurrency::class], version = 1)
abstract class CoinsDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    companion object {
        @Volatile
        private var instance : CoinsDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) : CoinsDatabase {
            return Room.databaseBuilder(context.applicationContext, CoinsDatabase::class.java, "coins")
                .createFromAsset("database/coins.db")
                .build()
        }
    }
}