package com.junka.tudu.task.data.di

import android.content.Context
import androidx.room.Room
import com.junka.tudu.task.data.TaskDao
import com.junka.tudu.task.data.TuduDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providesTaskDao(tuduDatabase: TuduDatabase)  :TaskDao = tuduDatabase.taskDao()

    @Singleton
    @Provides
    fun providesDataBase(@ApplicationContext context: Context): TuduDatabase =
        Room.databaseBuilder(context, TuduDatabase::class.java, "tudu.db").build()
}