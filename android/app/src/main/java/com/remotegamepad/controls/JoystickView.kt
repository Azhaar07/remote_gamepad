package com.remotegamepad.controls

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun JoystickView(
    size: Dp,
    onChanged: (Float, Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val translucent = Color.White.copy(alpha = 0.25f)
    Canvas(
        modifier = modifier
            .size(size)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        sendNormalized(offset, this.size.minDimension / 2f, onChanged)
                    },
                    onDragEnd = { onChanged(0f, 0f) },
                    onDragCancel = { onChanged(0f, 0f) }
                ) { change, _ ->
                    sendNormalized(change.position, this.size.minDimension / 2f, onChanged)
                }
            }
    ) {
        val radius = min(size.width, size.height) / 2f
        drawCircle(translucent, radius)
    }
}

private fun sendNormalized(position: Offset, radius: Float, onChanged: (Float, Float) -> Unit) {
    val cx = radius
    val cy = radius
    val dx = (position.x - cx) / radius
    val dy = (position.y - cy) / radius
    val mag = sqrt(dx * dx + dy * dy)
    if (mag <= 1f) {
        onChanged(dx.coerceIn(-1f, 1f), (-dy).coerceIn(-1f, 1f))
    } else {
        val angle = atan2(dy, dx)
        onChanged(cos(angle).toFloat(), (-sin(angle)).toFloat())
    }
}
