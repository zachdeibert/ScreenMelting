package com.gitlab.zachdeibert.screenmelting;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

public final class Main
{
    private static final DisplayWindow window;

    public static void main(final String[] args) throws AWTException
    {
        final Robot robot = new Robot();
        window.setImage(robot.createScreenCapture(new Rectangle(
                        Toolkit.getDefaultToolkit().getScreenSize())));
        window.setVisible(true);
    }

    static
    {
        window = new DisplayWindow();
    }
}
