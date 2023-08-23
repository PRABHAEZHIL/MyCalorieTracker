package com.kvep.tracker_presentation.search

import com.kvep.tracker_domain.model.TrackableFood

data class TrackableFoodUiState(
    val food:TrackableFood,
    val isExpanded:Boolean=false,
    val amount:String=""
)
