package com.example.colorpickerproject.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

fun getRandomColorHex(): String {
    val red = Random.nextInt(0, 256)
    val green = Random.nextInt(0, 256)
    val blue = Random.nextInt(0, 256)

    return String.format("#%02X%02X%02X", red, green, blue)
}

fun String.toColorLong(): Long {
    val cleanedHex = this.trimStart('#')
    if (cleanedHex.length != 6) {
        throw IllegalArgumentException("Hex code must be 6 characters.")
    }
    return ("FF$cleanedHex").toLong(16)
}

fun getCurrentDateFormatted(): String {
    val currentTimeMillis = System.currentTimeMillis()
    val date = Date(currentTimeMillis)
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(date)
}