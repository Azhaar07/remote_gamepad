package com.remotegamepad.ui

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.remotegamepad.model.GamepadState
import com.remotegamepad.model.SensorState
import com.remotegamepad.model.Vec2
import com.remotegamepad.network.BluetoothManager
import com.remotegamepad.network.NetworkManager
import com.remotegamepad.network.UsbManager
import com.remotegamepad.sensors.GamepadSensorManager
import com.remotegamepad.storage.LayoutStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GamepadViewModel(private val context: Context) : ViewModel() {
    private val networkManager = NetworkManager()
    private val bluetoothManager = BluetoothManager()
    private val usbManager = UsbManager()
    private val sensors = GamepadSensorManager(context)
    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    private val store = LayoutStore(context)

    private val _state = MutableStateFlow(GamepadState())
    val state: StateFlow<GamepadState> = _state

    val connected = networkManager.connected

    private val _editorMode = MutableStateFlow(false)
    val editorMode: StateFlow<Boolean> = _editorMode

    init {
        sensors.start()
        viewModelScope.launch {
            sensors.state.collect { s -> update { copy(sensors = s) } }
        }
    }

    fun toggleEditorMode() { _editorMode.value = !_editorMode.value }

    fun connectWifi(host: String, port: Int) = networkManager.start(host, port)
    fun disconnectWifi() = networkManager.stop()

    fun update(mutator: GamepadState.() -> GamepadState) {
        val next = _state.value.mutator().copy(timestampMs = System.currentTimeMillis())
        _state.value = next
        networkManager.latestState = next
        bluetoothManager.latestState = next
        usbManager.latestState = next
    }

    fun setLeftStick(x: Float, y: Float) = update { copy(leftStick = Vec2(x, y)) }
    fun setRightStick(x: Float, y: Float) = update { copy(rightStick = Vec2(x, y)) }

    fun pulse() {
        vibrator.vibrate(VibrationEffect.createOneShot(12, 80))
    }

    fun calibrateSensors() = sensors.calibrate()

    fun saveLayout(left: Pair<Float, Float>, right: Pair<Float, Float>) {
        viewModelScope.launch { store.save(left, right) }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            GamepadViewModel(context) as T
    }
}
