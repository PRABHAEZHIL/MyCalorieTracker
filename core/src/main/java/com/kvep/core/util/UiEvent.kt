package com.kvep.core.util

sealed class UiEvent{
    object Success:UiEvent()
    object NavigateUp:UiEvent()
    data class ShowSnackBar(val msg:UiText):UiEvent()
    data class ShowSnackBarString(val msg:String):UiEvent()
}
