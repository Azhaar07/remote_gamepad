namespace RemoteGamepad.Host.Services;

public sealed class UsbHandler
{
    public void Start()
    {
        Console.WriteLine("USB mode: run `adb forward tcp:5555 tcp:5555` then connect phone to localhost:5555.");
    }
}
