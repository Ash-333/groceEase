package com.ashish.groceease.di

import android.app.Application
import androidx.room.Room
import com.ashish.groceease.db.CartDao
import com.ashish.groceease.db.CartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesTodoDao(cartDatabase: CartDatabase):CartDao{
        return cartDatabase.cartDao()
    }

    @Provides
    @Singleton
    fun provideTodoDatabase(application: Application):CartDatabase{
        return Room.databaseBuilder(
            application,
            CartDatabase::class.java,
            "cart"
        ).fallbackToDestructiveMigration().build()
    }
}