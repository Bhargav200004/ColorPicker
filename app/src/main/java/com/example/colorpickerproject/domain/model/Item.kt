package com.example.colorpickerproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)  val id : Int = 0,
    val dateTime : String,
    val color : String
)