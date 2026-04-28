using System.Net;
using System.Net.Sockets;
using System.Text;
using RemoteGamepad.Host.Controllers;

namespace RemoteGamepad.Host.Networking;

public sealed class Server
{
    private readonly UdpClient _udp;
    private readonly ControllerEmulator _emulator;
    private readonly string _pin;
    private long _lastTimestamp;

    public Server(int port, ControllerEmulator emulator, string pin)
    {
        _udp = new UdpClient(new IPEndPoint(IPAddress.Any, port));
        _emulator = emulator;
        _pin = pin;
    }

    public async Task RunAsync(CancellationToken token)
    {
        Console.WriteLine("UDP server running...");
        while (!token.IsCancellationRequested)
        {
            var result = await _udp.ReceiveAsync(token);
            var json = Encoding.UTF8.GetString(result.Buffer);
            if (!InputParser.TryParse(json, out var packet))
            {
                continue;
            }

            if (packet.pin != _pin)
            {
                continue;
            }

            if (packet.ts < _lastTimestamp)
            {
                continue;
            }

            _lastTimestamp = packet.ts;
            _emulator.Apply(packet);
            Console.WriteLine($"{result.RemoteEndPoint} A:{packet.a} B:{packet.b} LX:{packet.lx:F2} LY:{packet.ly:F2}");
        }
    }
}
