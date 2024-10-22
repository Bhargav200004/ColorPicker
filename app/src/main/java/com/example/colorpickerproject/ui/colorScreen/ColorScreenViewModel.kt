package com.example.colorpickerproject.ui.colorScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorpickerproject.data.repository.FireStoreImpl
import com.example.colorpickerproject.domain.model.FireStore
import com.example.colorpickerproject.domain.repository.ItemRepository
import com.example.colorpickerproject.domain.model.Item
import com.example.colorpickerproject.utils.getCurrentDateFormatted
import com.example.colorpickerproject.utils.getRandomColorHex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ColorScreenViewModel @Inject constructor(
    private val repository: ItemRepository,
    private val fireStore: FireStore
) : ViewModel() {

    private var _state = MutableStateFlow(ColorScreenData())
    val state = _state.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = ColorScreenData()
        )

    val tasks: StateFlow<List<Item>> = repository.getAllData()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = emptyList()
        )

    init {
        getFirebaseCount()
    }


    fun onEvent(event: ColorScreenEvent) {
        when (event) {
            ColorScreenEvent.OnClickAddNewColor -> onNewColorButtonClick()
            ColorScreenEvent.OnClickSyncButton -> onClickSyncButtonClick()
        }
    }

    private fun onClickSyncButtonClick() {
        viewModelScope.launch {

            try {
                _state.update {state->
                    state.copy(
                        isSyncing = true
                    )
                }
                val data = tasks.value
                var firebaseCount = fireStore.getUserDataCount()
                if ((data.size - (firebaseCount?: 0).toInt()) != 0){
                    data.forEach { item ->
                        fireStore.sendColorData(item)
                    }
                }
                delay(50)
                firebaseCount = fireStore.getUserDataCount()
                Log.d("ColorScreen" , firebaseCount.toString())
                _state.update { state->
                    state.copy(
                        syncNumber = (tasks.value.size - (firebaseCount?: 0)).toString(),
                        isSyncing = false
                    )
                }
            }
            catch (e : Exception){
                Log.e("ColorScreen", "${e.message}")
            }
        }
    }

    private fun getFirebaseCount() {
        viewModelScope.launch {
            val firebaseCount = fireStore.getUserDataCount()
            _state.update { state ->
                state.copy(
                    syncNumber = if(tasks.value.size - (firebaseCount ?: 0) <= 0) "0" else (tasks.value.size - (firebaseCount ?: 0)).toString()
                )
            }
        }
    }




    private fun onNewColorButtonClick() {
        viewModelScope.launch {
            try {
                repository.insert(
                    Item(
                        color = getRandomColorHex(),
                        dateTime = getCurrentDateFormatted(),
                    )
                )
                delay(50)

                val firebaseCount = fireStore.getUserDataCount()
                _state.update { state->
                    state.copy(
                        syncNumber = (tasks.value.size - (firebaseCount?: 0)).toString()
                    )
                }
            } catch (e: Exception) {
                Log.e("ColorScreen", "${e.message}")
            }
        }
    }
}


