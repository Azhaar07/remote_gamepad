package com.remotegamepad.network

import com.remotegamepad.model.GamepadState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.net.Socket

class UsbManager {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null
    @Volatile
    var latestState: GamepadState = GamepadState()

    fun start(port: Int = 5555) {
        job?.cancel()
        job = scope.launch {
            Socket("127.0.0.1", port).use { socket ->
                val out = socket.getOutputStream()
                while (isActive) {
                    out.write((PacketSerializer.toJson(latestState) + "\n").toByteArray())
                    out.flush()
                    delay(16L)
                }
            }
        }
    }

    fun stop() = job?.cancel()
}
