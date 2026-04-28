using Nefarius.ViGEm.Client;
using Nefarius.ViGEm.Client.Targets;
using Nefarius.ViGEm.Client.Targets.Xbox360;
using RemoteGamepad.Host.Models;

namespace RemoteGamepad.Host.Controllers;

public sealed class ControllerEmulator : IDisposable
{
    private readonly ViGEmClient _client;
    private readonly IXbox360Controller _controller;

    public ControllerEmulator()
    {
        _client = new ViGEmClient();
        _controller = _client.CreateXbox360Controller();
        _controller.Connect();
    }

    public void Apply(GamepadPacket p)
    {
        _controller.SetAxisValue(Xbox360Axis.LeftThumbX, ScaleAxis(p.lx));
        _controller.SetAxisValue(Xbox360Axis.LeftThumbY, ScaleAxis(p.ly));
        _controller.SetAxisValue(Xbox360Axis.RightThumbX, ScaleAxis(p.rx));
        _controller.SetAxisValue(Xbox360Axis.RightThumbY, ScaleAxis(p.ry));
        _controller.SetSliderValue(Xbox360Slider.LeftTrigger, (byte)(Math.Clamp(p.lt, 0, 1) * 255));
        _controller.SetSliderValue(Xbox360Slider.RightTrigger, (byte)(Math.Clamp(p.rt, 0, 1) * 255));

        _controller.SetButtonState(Xbox360Button.A, p.a);
        _controller.SetButtonState(Xbox360Button.B, p.b);
        _controller.SetButtonState(Xbox360Button.X, p.x);
        _controller.SetButtonState(Xbox360Button.Y, p.y);
        _controller.SetButtonState(Xbox360Button.LeftShoulder, p.lb);
        _controller.SetButtonState(Xbox360Button.RightShoulder, p.rb);
        _controller.SetButtonState(Xbox360Button.Start, p.start);
        _controller.SetButtonState(Xbox360Button.Back, p.select);
        _controller.SetButtonState(Xbox360Button.Up, p.du);
        _controller.SetButtonState(Xbox360Button.Down, p.dd);
        _controller.SetButtonState(Xbox360Button.Left, p.dl);
        _controller.SetButtonState(Xbox360Button.Right, p.dr);

        _controller.SubmitReport();
    }

    private static short ScaleAxis(float value) => (short)(Math.Clamp(value, -1f, 1f) * short.MaxValue);

    public void Dispose()
    {
        _controller.Disconnect();
        _client.Dispose();
    }
}
