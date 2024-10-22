package com.example.colorpickerproject.di

import android.content.Context
import androidx.room.Room
import com.example.colorpickerproject.data.local.AppDatabase
import com.example.colorpickerproject.data.local.ItemDao
import com.example.colorpickerproject.data.repository.FireStoreImpl
import com.example.colorpickerproject.domain.model.FireStore
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @Singleton
    fun getFireBaseStore() : FirebaseFirestore {
        return Firebase.firestore
    }


    @Provides
    @Singleton
    fun provideFireStoreRepositoryImp(
        fireStore: FirebaseFirestore
    ) : FireStore {
        return FireStoreImpl(db = fireStore)
    }



    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) : AppDatabase =
        Room.databaseBuilder(context , AppDatabase::class.java , "Color.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideItemDao(appDatabase: AppDatabase) : ItemDao =
        appDatabase.itemDao()


}

