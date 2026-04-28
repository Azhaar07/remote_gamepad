# Windows Setup

## Prerequisites
1. Install **Visual Studio 2022** with .NET desktop development workload.
2. Install **.NET 8 SDK**.
3. Install **ViGEmBus** driver from Nefarius project releases.
4. Reboot after ViGEmBus installation.

## Build EXE
1. Open `windows/RemoteGamepad.sln` in Visual Studio.
2. Set configuration to **Release** and target **x64**.
3. Right-click solution and click **Build Solution**.
4. Output executable:
   - `windows/src/RemoteGamepad.Host/bin/Release/net8.0-windows/RemoteGamepad.Host.exe`

## Run Host
1. Launch `RemoteGamepad.Host.exe`.
2. Type `start` and press Enter to start UDP server.
3. Keep console open while playing.

## Verify ViGEm Emulation
1. Open `joy.cpl` from Start menu.
2. Confirm an Xbox 360 controller appears when host app is running.
3. Open your game and map controls if needed.
