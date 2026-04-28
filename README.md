# RemoteGamepad

RemoteGamepad turns an Android phone into a low-latency controller for Windows games. The Android app streams gamepad state over Wi-Fi (UDP), Bluetooth SPP, or USB (ADB port forwarding), and the Windows host maps incoming input to an emulated Xbox 360 controller through ViGEmBus.

## Features

- Android Jetpack Compose controller UI (Material 3 dark theme)
- Dual analog sticks with normalized output
- XYAB, D-pad, LB/RB, LT/RT, Start/Select buttons
- Multi-touch support and haptic feedback
- Sensor steering mode (gyro + accelerometer) with calibration
- Layout editor mode: drag, resize, save local profile
- Transport modes:
  - Wi-Fi UDP (primary)
  - Bluetooth SPP
  - USB via ADB forward
- Windows receiver with:
  - Start/Stop server
  - Connection status
  - Device detection hints
  - Input debug output
  - Xbox 360 emulation using ViGEmBus
- Packet throttling to 60 Hz and old-packet drop strategy
- Optional local PIN validation for pairing

## Screenshots

- `docs/screenshots/android_controller.png` *(placeholder)*
- `docs/screenshots/windows_host.png` *(placeholder)*

## Repository Structure

```text
RemoteGamepad/
├── android/
│   ├── app/
│   ├── build.gradle
│   ├── settings.gradle
│   └── gradle.properties
├── windows/
│   ├── RemoteGamepad.sln
│   └── src/
│       └── RemoteGamepad.Host/
├── docs/
│   ├── setup_android.md
│   ├── setup_windows.md
│   └── connection_guide.md
├── README.md
└── .gitignore
```

## Quick Start

### Android
1. Open Android Studio Panda.
2. Click **Open Project**.
3. Select `android/`.
4. Wait for Gradle sync.
5. Connect a physical Android device and press **Run**.

### Windows
1. Install Visual Studio 2022 with .NET desktop workload.
2. Install ViGEmBus driver.
3. Open `windows/RemoteGamepad.sln`.
4. Build Release x64.
5. Run `RemoteGamepad.Host.exe`.

Detailed setup is in `docs/`.

## Security Notes

- Host binds to local subnet by default.
- Client supports optional PIN included in each packet.
- For internet exposure, add a secure tunnel with encryption and authentication (not included in MVP).

## Troubleshooting

- **No controller appears in game:** Verify ViGEmBus is installed and service is active.
- **High latency on Wi-Fi:** Use 5 GHz network, reduce interference, keep phone near router.
- **No USB data:** Confirm `adb devices` sees phone and run `adb forward tcp:5555 tcp:5555`.
- **Bluetooth fails:** Confirm Windows and phone pairing and SPP support.
- **App does not connect:** Ensure host IP and port match app settings.

## Build Instructions

### Android build APK
1. Open `android/` in Android Studio Panda.
2. Build > Build Bundle(s)/APK(s) > Build APK(s).
3. Retrieve APK from `android/app/build/outputs/apk/debug/`.

### Windows build EXE
1. Open `windows/RemoteGamepad.sln` in Visual Studio.
2. Set configuration to Release and platform x64.
3. Build solution.
4. EXE output: `windows/src/RemoteGamepad.Host/bin/Release/net8.0-windows/`.
