package com.gitlab.zachdeibert.screenmelting;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

public final class Main
{
    private static final DisplayWindow window;

    public static void main(final String[] args) throws AWTException
    {
        final Robot robot = new Robot();
        final Toolkit tk = Toolkit.getDefaultToolkit();
        final GraphicsEnvironment gfxEnv = GraphicsEnvironment
                        .getLocalGraphicsEnvironment();
        final GraphicsDevice gfxDev = gfxEnv.getScreenDevices()[0];
        try
        {
            window.setBounds(new Rectangle(tk.getScreenSize()));
            window.setImage(robot.createScreenCapture(new Rectangle(
                            Toolkit.getDefaultToolkit().getScreenSize())));
            if (gfxDev.isFullScreenSupported())
            {
                gfxDev.setFullScreenWindow(window);
            }
            window.setVisible(true);
        }
        finally
        {
            if (gfxDev.isFullScreenSupported())
            {
                gfxDev.setFullScreenWindow(null);
            }
        }
    }

    static
    {
        window = new DisplayWindow();
    }
}
