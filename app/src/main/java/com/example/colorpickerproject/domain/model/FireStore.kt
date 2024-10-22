package com.example.colorpickerproject.domain.model

import kotlinx.coroutines.flow.Flow

interface FireStore {

    suspend fun sendColorData(colorData: Item)

    suspend fun getUserDataCount(): Long?
}