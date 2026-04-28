package com.remotegamepad.network

import com.remotegamepad.model.GamepadState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class NetworkManager {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var senderJob: Job? = null
    private val _connected = MutableStateFlow(false)
    val connected: StateFlow<Boolean> = _connected

    @Volatile
    var latestState: GamepadState = GamepadState()

    fun start(host: String, port: Int) {
        senderJob?.cancel()
        senderJob = scope.launch {
            DatagramSocket().use { socket ->
                val address = InetAddress.getByName(host)
                _connected.value = true
                while (isActive) {
                    val payload = PacketSerializer.toJson(latestState).toByteArray()
                    val packet = DatagramPacket(payload, payload.size, address, port)
                    socket.send(packet)
                    delay(16L)
                }
            }
        }
    }

    fun stop() {
        senderJob?.cancel()
        _connected.value = false
    }
}
