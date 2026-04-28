using System.Text.Json;
using RemoteGamepad.Host.Models;

namespace RemoteGamepad.Host.Networking;

public static class InputParser
{
    private static readonly JsonSerializerOptions Options = new(JsonSerializerDefaults.Web);

    public static bool TryParse(string json, out GamepadPacket packet)
    {
        try
        {
            packet = JsonSerializer.Deserialize<GamepadPacket>(json, Options) ?? new GamepadPacket();
            return true;
        }
        catch
        {
            packet = new GamepadPacket();
            return false;
        }
    }
}
