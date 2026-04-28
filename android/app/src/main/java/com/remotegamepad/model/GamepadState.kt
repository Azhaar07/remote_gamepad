package com.remotegamepad.model

data class Vec2(val x: Float = 0f, val y: Float = 0f)

data class SensorState(
    val roll: Float = 0f,
    val pitch: Float = 0f,
    val yaw: Float = 0f
)

data class GamepadState(
    val leftStick: Vec2 = Vec2(),
    val rightStick: Vec2 = Vec2(),
    val dpadUp: Boolean = false,
    val dpadDown: Boolean = false,
    val dpadLeft: Boolean = false,
    val dpadRight: Boolean = false,
    val buttonA: Boolean = false,
    val buttonB: Boolean = false,
    val buttonX: Boolean = false,
    val buttonY: Boolean = false,
    val lb: Boolean = false,
    val rb: Boolean = false,
    val lt: Float = 0f,
    val rt: Float = 0f,
    val start: Boolean = false,
    val select: Boolean = false,
    val steeringEnabled: Boolean = false,
    val sensors: SensorState = SensorState(),
    val pin: String = "0000",
    val timestampMs: Long = System.currentTimeMillis()
)
