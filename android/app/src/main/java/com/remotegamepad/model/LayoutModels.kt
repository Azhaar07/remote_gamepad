package com.remotegamepad.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ControlLayout(
    val id: String,
    val center: Offset,
    val size: Dp
)
