package com.namanh.kybersearch.di

import android.content.Context
import androidx.room.Room
import com.namanh.kybersearch.data.database.AppDatabase
import com.namanh.kybersearch.data.database.CoinDao
import com.namanh.kybersearch.data.database.RecentSearchDao
import com.namanh.kybersearch.data.service.ApiService
import com.namanh.kybersearch.utils.AppUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesApiService(): ApiService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AppUtils.BASE_URL)
            .build().create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppUtils.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun providesCoinDao(appDatabase: AppDatabase): CoinDao = appDatabase.coinDao()

    @Singleton
    @Provides
    fun providesRecentSearchDao(appDatabase: AppDatabase): RecentSearchDao = appDatabase.recentSearchDao()

    @DispatcherIO
    @Singleton
    @Provides
    fun providesDispatcherIO(): CoroutineDispatcher = Dispatchers.IO
}