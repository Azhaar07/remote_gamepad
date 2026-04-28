using RemoteGamepad.Host.Controllers;
using RemoteGamepad.Host.Networking;
using RemoteGamepad.Host.Services;

Console.WriteLine("RemoteGamepad Host");
Console.WriteLine("Commands: start, stop, bt, usb, exit");

using var emulator = new ControllerEmulator();
var bt = new BluetoothHandler();
var usb = new UsbHandler();
CancellationTokenSource? cts = null;
Task? serverTask = null;

while (true)
{
    Console.Write("> ");
    var cmd = Console.ReadLine()?.Trim().ToLowerInvariant();

    switch (cmd)
    {
        case "start":
            if (serverTask is { IsCompleted: false })
            {
                Console.WriteLine("Server already running.");
                break;
            }

            cts = new CancellationTokenSource();
            var server = new Server(port: 5555, emulator, pin: "0000");
            serverTask = Task.Run(() => server.RunAsync(cts.Token));
            Console.WriteLine("Server started on UDP 5555.");
            break;

        case "stop":
            cts?.Cancel();
            Console.WriteLine("Server stopped.");
            break;

        case "bt":
            bt.Start();
            break;

        case "usb":
            usb.Start();
            break;

        case "exit":
            cts?.Cancel();
            if (serverTask != null)
            {
                try { await serverTask; } catch (OperationCanceledException) { }
            }
            return;

        default:
            Console.WriteLine("Unknown command.");
            break;
    }
}
