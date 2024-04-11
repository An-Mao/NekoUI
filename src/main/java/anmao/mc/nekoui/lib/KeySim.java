package anmao.mc.nekoui.lib;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import java.awt.*;

public class KeySim {
    public static void simulateKeyPress(int keyCode, Window window) {
        // 获取窗口句柄
        WinDef.HWND hWnd = new WinDef.HWND(Native.getWindowPointer(window));

        // 创建输入结构体数组，用于存储按键序列
        WinUser.INPUT[] inputs = new WinUser.INPUT[2];
        inputs[0] = new WinUser.INPUT();
        inputs[1] = new WinUser.INPUT();

        // 设置按键按下事件
        inputs[0].type = new WinUser.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        inputs[0].input.setType("ki");
        inputs[0].input.ki.wVk = new WinDef.WORD(keyCode);
        inputs[0].input.ki.dwFlags = new WinDef.DWORD(0);

        // 设置按键释放事件
        inputs[1].type = new WinUser.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        inputs[1].input.setType("ki");
        inputs[1].input.ki.wVk = new WinDef.WORD(keyCode);
        inputs[1].input.ki.dwFlags = new WinDef.DWORD(WinUser.KEYBDINPUT.KEYEVENTF_KEYUP);

        // 发送按键事件到指定窗口
        User32.INSTANCE.SendInput(new WinDef.DWORD(2), inputs, inputs[0].size());
    }

    public interface User32 extends com.sun.jna.platform.win32.User32 {
        User32 INSTANCE = Native.load("user32", User32.class);
    }

}
