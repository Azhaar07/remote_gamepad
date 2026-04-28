package com.remotegamepad.network

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import com.remotegamepad.model.GamepadState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.UUID

class BluetoothManager {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null
    @Volatile
    var latestState: GamepadState = GamepadState()

    @SuppressLint("MissingPermission")
    fun start(device: BluetoothDevice) {
        val adapter = BluetoothAdapter.getDefaultAdapter() ?: return
        if (!adapter.isEnabled) return
        job?.cancel()
        job = scope.launch {
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
            val socket: BluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
            socket.connect()
            socket.outputStream.use { out ->
                while (isActive) {
                    out.write((PacketSerializer.toJson(latestState) + "\n").toByteArray())
                    out.flush()
                    kotlinx.coroutines.delay(16L)
                }
            }
            socket.close()
        }
    }

    fun stop() = job?.cancel()
}
