# Connection Guide

## 1) Wi-Fi (UDP, recommended)
1. Connect phone and PC to the same router/AP.
2. Find PC local IPv4 (e.g. `ipconfig` -> `192.168.1.2`).
3. In host app, run `start` (UDP port 5555).
4. In Android app, set host to PC IP and port `5555`.
5. Press **Connect WiFi**.
6. Move sticks; host console should show live values.

## 2) Bluetooth (SPP)
1. Pair phone and Windows PC from system Bluetooth settings.
2. Start host app and run `bt` command.
3. In Android app select Bluetooth mode and paired device.
4. Ensure SPP/RFCOMM capability exists on target adapter stack.

## 3) USB (ADB forwarding)
1. Connect Android phone via USB data cable.
2. Run:
   ```bash
   adb devices
   adb forward tcp:5555 tcp:5555
   ```
3. Start host server (`start`).
4. In Android app choose USB mode.
5. App sends data to `127.0.0.1:5555` through ADB tunnel.

## PIN Pairing
- Default PIN is `0000` on both Android and Windows.
- Change PIN in app and host source for private local sessions.
- Packets with wrong PIN are dropped.
