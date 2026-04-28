package com.remotegamepad.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.remotegamepad.model.SensorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GamepadSensorManager(context: Context) : SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    private val accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val _state = MutableStateFlow(SensorState())
    val state: StateFlow<SensorState> = _state

    private var rollOffset = 0f
    private var pitchOffset = 0f

    fun start() {
        gyro?.also { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME) }
        accel?.also { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME) }
    }

    fun stop() = sensorManager.unregisterListener(this)

    fun calibrate() {
        rollOffset = _state.value.roll
        pitchOffset = _state.value.pitch
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            _state.value = _state.value.copy(
                yaw = event.values[2],
                roll = event.values[0] - rollOffset,
                pitch = event.values[1] - pitchOffset
            )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
}
