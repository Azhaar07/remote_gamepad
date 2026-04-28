# Android Setup (Android Studio Panda)

## Prerequisites
1. Install **Android Studio Panda (2024.1.1+)** from the official Android Developer site.
2. Install SDK Platform 34 and Android SDK Build-Tools 34.
3. Enable Developer Options + USB debugging on your phone.

## Open Project
1. Launch Android Studio Panda.
2. Click **Open**.
3. Select the `android/` folder from this repository.
4. Allow trust/sync prompts.
5. Wait for Gradle sync to finish successfully.

## Run on Device
1. Connect Android phone over USB.
2. Accept the debugging fingerprint prompt on phone.
3. Select your device in the top toolbar.
4. Press **Run app**.

## Build APK
1. In Android Studio: **Build > Build Bundle(s)/APK(s) > Build APK(s)**.
2. After build success, click the notification link.
3. APK path: `android/app/build/outputs/apk/debug/app-debug.apk`.

## Recommended Runtime Settings
- Disable battery optimization for RemoteGamepad app.
- Keep screen refresh at 60Hz+.
- Keep device on same fast Wi-Fi AP for minimal latency.
