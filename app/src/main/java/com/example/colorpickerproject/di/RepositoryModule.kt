package com.example.colorpickerproject.di

import com.example.colorpickerproject.domain.repository.ItemRepository
import com.example.colorpickerproject.data.repository.ItemRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(impl: ItemRepositoryImpl)
    : ItemRepository
}