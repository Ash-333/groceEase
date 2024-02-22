package com.ashish.groceease.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CartItem::class],version=1, exportSchema = false)
abstract class CartDatabase:RoomDatabase() {
    abstract fun cartDao():CartDao
}