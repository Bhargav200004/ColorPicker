package com.example.colorpickerproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.colorpickerproject.ui.colorScreen.ColorScreen
import com.example.colorpickerproject.ui.colorScreen.ColorScreenTopAppBar
import com.example.colorpickerproject.ui.theme.ColorPickerProjectTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ColorPickerProjectTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) {  innerPadding ->
                        ColorScreen(modifier = Modifier
                            .padding(paddingValues = innerPadding))
                }
            }
        }
    }
}
