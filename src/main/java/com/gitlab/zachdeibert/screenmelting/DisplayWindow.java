package com.gitlab.zachdeibert.screenmelting;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

final class DisplayWindow extends Frame
{
    private static final long serialVersionUID = -1766449888270618035L;
    private final ImageManipulator manipulator;
    private final Random rand;
    private Image image;
    private int frame;

    public Image getImage()
    {
        return image;
    }

    public void setImage(final Image image)
    {
        this.image = image;
    }

    @Override
    public void paint(final Graphics g)
    {
        g.drawImage(image, 0, 0, this);
        EventQueue.invokeLater(() -> {
            manipulator.manipulate(image, frame++, rand, getSize());
            repaint();
        });
    }

    @Override
    public void update(final Graphics g)
    {
        paint(g);
    }

    DisplayWindow()
    {
        setAlwaysOnTop(true);
        setFocusable(false);
        setResizable(false);
        setTitle("");
        setUndecorated(true);
        manipulator = new ImageManipulator();
        rand = new Random();
        image = null;
        frame = 0;
    }
}
