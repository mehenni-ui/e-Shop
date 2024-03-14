package com.example.elalmashop.data.db.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.elalmashop.data.model.FavProduct
import com.example.elalmashop.data.model.Product
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@TypeConverters(Converter::class)
@Database(entities = [Product::class, FavProduct::class], version = 4, exportSchema = false)
abstract class ProductDb: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun favProductDao(): FavProductDao

    companion object{
        @Volatile
        private var instance: ProductDb? = null
        private val LOCK = Any()

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE favProduct ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
            }
        }





        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: createDB(context).also{
                instance = it
            }
        }



        private fun createDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ProductDb::class.java,
                "cart_db"
            )
                .addMigrations(MIGRATION_3_4)
                .build()
    }
}