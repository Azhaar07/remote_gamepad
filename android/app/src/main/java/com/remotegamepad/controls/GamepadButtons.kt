package com.remotegamepad.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun RoundButton(label: String, pressed: Boolean, onPress: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(
                color = if (pressed) Color(0xAA00FFAA) else Color(0x66FFFFFF),
                shape = CircleShape
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        onPress(true)
                        tryAwaitRelease()
                        onPress(false)
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Color.White)
    }
}
