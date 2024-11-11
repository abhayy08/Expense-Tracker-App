package com.abhay.expensetracker.di

import android.content.Context
import androidx.room.Room
import com.abhay.expensetracker.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

//    @Singleton
//    @Provides
//    fun providePreferenceManager(@ApplicationContext context: Context): UIModeImpl {
//        return UIModeDataStore(context)
//    }

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "transaction.db")
            .fallbackToDestructiveMigration().build()
    }
//
//    @Singleton
//    @Provides
//    fun provideExportCSV(@ApplicationContext context: Context): ExportCsvService {
//        return ExportCsvService(appContext = context)
//    }
}
