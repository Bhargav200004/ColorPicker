package com.example.colorpickerproject.ui.colorScreen

sealed class ColorScreenEvent {

    data object OnClickAddNewColor : ColorScreenEvent()

    data object OnClickSyncButton : ColorScreenEvent()


}