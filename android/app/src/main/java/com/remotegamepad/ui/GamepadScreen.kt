package com.remotegamepad.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.remotegamepad.controls.JoystickView
import com.remotegamepad.controls.RoundButton

@Composable
fun GamepadScreen(vm: GamepadViewModel) {
    val state by vm.state.collectAsState()
    val connected by vm.connected.collectAsState()
    val editorMode by vm.editorMode.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { if (connected) vm.disconnectWifi() else vm.connectWifi("192.168.1.2", 5555) }) {
                    Text(if (connected) "Disconnect" else "Connect WiFi")
                }
                Text(if (connected) "Connected" else "Offline", color = if (connected) Color.Green else Color.Red)
                Text("Editor")
                Switch(checked = editorMode, onCheckedChange = { vm.toggleEditorMode() })
                Button(onClick = vm::calibrateSensors) { Text("Calibrate") }
                Button(onClick = { vm.update { copy(steeringEnabled = !steeringEnabled) } }) { Text("Steering") }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                RoundButton("Start", state.start) { vm.update { copy(start = it) } }
                RoundButton("Select", state.select) { vm.update { copy(select = it) } }
                RoundButton("↑", state.dpadUp) { vm.update { copy(dpadUp = it) } }
                RoundButton("↓", state.dpadDown) { vm.update { copy(dpadDown = it) } }
                RoundButton("←", state.dpadLeft) { vm.update { copy(dpadLeft = it) } }
                RoundButton("→", state.dpadRight) { vm.update { copy(dpadRight = it) } }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("LT")
                Slider(value = state.lt, onValueChange = { vm.update { copy(lt = it) } }, valueRange = 0f..1f, modifier = Modifier.weight(1f))
                Text("RT")
                Slider(value = state.rt, onValueChange = { vm.update { copy(rt = it) } }, valueRange = 0f..1f, modifier = Modifier.weight(1f))
            }

            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
                JoystickView(size = 180.dp, onChanged = vm::setLeftStick)

                Column(verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        RoundButton("Y", state.buttonY) { vm.update { copy(buttonY = it) }; if (it) vm.pulse() }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        RoundButton("X", state.buttonX) { vm.update { copy(buttonX = it) }; if (it) vm.pulse() }
                        RoundButton("B", state.buttonB) { vm.update { copy(buttonB = it) }; if (it) vm.pulse() }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        RoundButton("A", state.buttonA) { vm.update { copy(buttonA = it) }; if (it) vm.pulse() }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        RoundButton("LB", state.lb) { vm.update { copy(lb = it) } }
                        RoundButton("RB", state.rb) { vm.update { copy(rb = it) } }
                    }
                }

                JoystickView(size = 180.dp, onChanged = vm::setRightStick)
            }
        }
    }
}
