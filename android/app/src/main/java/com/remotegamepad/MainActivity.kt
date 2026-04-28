package com.remotegamepad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.remotegamepad.ui.GamepadScreen
import com.remotegamepad.ui.GamepadViewModel
import com.remotegamepad.theme.RemoteGamepadTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemoteGamepadTheme {
                val vm: GamepadViewModel = viewModel(factory = GamepadViewModel.Factory(this))
                GamepadScreen(vm)
            }
        }
    }
}
