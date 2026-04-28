package com.remotegamepad.network

import com.remotegamepad.model.GamepadState
import org.json.JSONObject

object PacketSerializer {
    fun toJson(state: GamepadState): String {
        return JSONObject()
            .put("lx", state.leftStick.x)
            .put("ly", state.leftStick.y)
            .put("rx", state.rightStick.x)
            .put("ry", state.rightStick.y)
            .put("du", state.dpadUp)
            .put("dd", state.dpadDown)
            .put("dl", state.dpadLeft)
            .put("dr", state.dpadRight)
            .put("a", state.buttonA)
            .put("b", state.buttonB)
            .put("x", state.buttonX)
            .put("y", state.buttonY)
            .put("lb", state.lb)
            .put("rb", state.rb)
            .put("lt", state.lt)
            .put("rt", state.rt)
            .put("start", state.start)
            .put("select", state.select)
            .put("steering", state.steeringEnabled)
            .put("roll", state.sensors.roll)
            .put("pitch", state.sensors.pitch)
            .put("yaw", state.sensors.yaw)
            .put("pin", state.pin)
            .put("ts", state.timestampMs)
            .toString()
    }
}
