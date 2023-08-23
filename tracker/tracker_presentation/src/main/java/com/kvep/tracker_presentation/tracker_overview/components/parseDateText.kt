package com.kvep.tracker_presentation.tracker_overview.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.runtime.Composable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun parseDateText(date: LocalDate): String {
    val today = LocalDate.now()
    return when(date){
        today->"Today"
        today.minusDays(1)->"Yesterday"
        today.plusDays(1)->"Tomorrow"
        else->DateTimeFormatter.ofPattern("dd LLLL").format(date)
    }

}