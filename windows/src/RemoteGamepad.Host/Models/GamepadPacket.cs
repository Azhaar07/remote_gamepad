namespace RemoteGamepad.Host.Models;

public sealed class GamepadPacket
{
    public float lx { get; set; }
    public float ly { get; set; }
    public float rx { get; set; }
    public float ry { get; set; }
    public bool du { get; set; }
    public bool dd { get; set; }
    public bool dl { get; set; }
    public bool dr { get; set; }
    public bool a { get; set; }
    public bool b { get; set; }
    public bool x { get; set; }
    public bool y { get; set; }
    public bool lb { get; set; }
    public bool rb { get; set; }
    public float lt { get; set; }
    public float rt { get; set; }
    public bool start { get; set; }
    public bool select { get; set; }
    public bool steering { get; set; }
    public float roll { get; set; }
    public float pitch { get; set; }
    public float yaw { get; set; }
    public string pin { get; set; } = "0000";
    public long ts { get; set; }
}
