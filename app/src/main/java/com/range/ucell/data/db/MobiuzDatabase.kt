package com.range.ucell.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.range.ucell.data.db.entity.*


@Database(
        entities = [LangModel::class, PacketModel::class, MinutesModel::class, RateModel::class, ServiceModel::class, DealerCode::class, SaleModel::class, BannerModel::class, UssdCodeModel::class],
        version = 12
)
abstract class MobiuzDatabase : RoomDatabase() {

    abstract fun mobiuzDao(): MobiuzDao

    companion object {

        @Volatile
        private var instance: MobiuzDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        MobiuzDatabase::class.java, "mobiuz.db")
                        .fallbackToDestructiveMigration()
                        .build()
    }
}