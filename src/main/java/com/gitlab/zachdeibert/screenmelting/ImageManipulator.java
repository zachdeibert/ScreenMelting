package com.gitlab.zachdeibert.screenmelting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.Raster;
import java.util.Random;

final class ImageManipulator
{
    private static final int[] probability = new int[] { 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2 };
    private Image              lastImage;
    private int[]              melted;
    private Color              meltColor;

    void generateColor(final Image image, final Dimension size)
    {
        final BufferedImage buffer = new BufferedImage(size.width, size.height,
                        BufferedImage.TYPE_INT_ARGB);
        final Graphics g = buffer.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        final Raster r = buffer.getData();
        final float[] tmp = new float[4];
        final double[] sum = new double[3];
        for (int x = 0; x < size.width; x++)
        {
            for (int y = 0; y < size.height; y++)
            {
                r.getPixel(x, y, tmp);
                sum[0] += tmp[0];
                sum[1] += tmp[1];
                sum[2] += tmp[2];
            }
        }
        double pixels = size.width * size.height * 256;
        meltColor = new Color((float) (sum[0] / pixels),
                        (float) (sum[1] / pixels), (float) (sum[2] / pixels));
    }

    void manipulate(final Image image, final int frame, final Random rand,
                    final Dimension size)
    {
        final Graphics g = image.getGraphics();
        final int width = size.width;
        if (image != lastImage)
        {
            lastImage = image;
            melted = new int[width];
            for (int i = 0; i < width; i++)
            {
                melted[i] = 0;
            }
            generateColor(image, size);
        }
        g.setColor(meltColor);
        int min = size.height;
        int max = 0;
        for (int i = 0; i < width; i++)
        {
            melted[i] += probability[rand.nextInt(probability.length)];
            g.fillRect(i, 0, 1, melted[i]);
            min = Math.min(melted[i], min);
            max = Math.max(melted[i], max);
        }
        //@formatter:off
        //*
        //@formatter:on
        final Kernel kernel = new Kernel(3, 3,
                        new float[] { 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f,
                                        1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f,
                                        1f / 9f });
        final ConvolveOp convolve = new ConvolveOp(kernel);
        final int height = max - min;
        final BufferedImage bi = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_ARGB);
        final Graphics biG = bi.getGraphics();
        biG.drawImage(image, 0, 0, width, height, 0, min, width, min + height,
                        null);
        biG.dispose();
        g.drawImage(convolve.filter(bi, null), 0, min, null);
        //@formatter:off
        //-*/
        //@formatter:on
        g.dispose();
        if (min == size.height)
        {
            System.exit(0);
        }
    }
}
