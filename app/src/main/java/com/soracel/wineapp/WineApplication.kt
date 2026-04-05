package com.soracel.wineapp

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.db.SupportSQLiteDatabase

class WineApplication: Application() {
    companion object {
        lateinit var database : WineDatabase
    }

    override fun onCreate() {
        super.onCreate()

        val MIGRATION_1_2 = object: Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE wines ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
            }
        }

        database = Room
            .databaseBuilder(this,
                WineDatabase::class.java,
                "wines_db")
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}