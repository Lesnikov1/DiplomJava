package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;


public class Converter implements TextGraphicsConverter {
    private int width;
    private int height;
    private double maxRatio;
    TextColorSchema schema;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        int newWidth = 0;
        int newHeight = 0;
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        double ratio = imgWidth * 1.0 / imgHeight;

        if (getMaxRatio() != 0) {
            if (ratio >= 1 / maxRatio && ratio <= maxRatio) {
            } else
                throw new BadImageSizeException(ratio, maxRatio);
        }
        if (width == 0 && height == 0) {
            newWidth = imgWidth;
            newHeight = imgHeight;
        } else if (width != 0 && height != 0) {
            double ratioWidth = width * 1.0 / imgWidth; //i
            double ratioHeight = height * 1.0 / imgHeight;// j
            if (ratioWidth < 1 || ratioHeight < 1) {
                if (ratioWidth < ratioHeight) {
                    newWidth = (int) (imgWidth * ratioWidth);
                    newHeight = (int) (imgHeight * ratioWidth);
                } else {
                    newWidth = (int) (imgWidth * ratioHeight);
                    newHeight = (int) (imgHeight * ratioHeight);
                }
            } else {
                newWidth = imgWidth;
                newHeight = imgHeight;
            }
        } else if (width != 0) {
            double ratioWidth = width * 1.0 / imgWidth;
            if (ratioWidth < 1) {
                newWidth = (int) (imgWidth * ratioWidth);
                newHeight = (int) (imgHeight * ratioWidth);
            } else {
                newWidth = imgWidth;
                newHeight = imgHeight;
            }
        } else {
            double ratioHeight = height * 1.0 / imgHeight;
            if (ratioHeight < 1) {
                newWidth = (int) (imgWidth * ratioHeight);
                newHeight = (int) (imgHeight * ratioHeight);
            } else {
                newWidth = imgWidth;
                newHeight = imgHeight;
            }
        }
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        RenderedImage imageObject = bwImg;
        ImageIO.write(imageObject, "png", new File("out.png"));
        WritableRaster bwRaster = bwImg.getRaster();

        Schema schema = new Schema();
        int newImgArrLength = (newHeight - 1) * (newWidth - 1) + newHeight;
        char[] newArrImg = new char[newImgArrLength];
        int k = 0;

        for (int i = 0; i < newHeight - 1; i++) {
            for (int j = 0; j < newWidth - 1; j++) {
                int color = bwRaster.getPixel(j, i, new int[3])[0];
                char c = schema.convert(color);
                newArrImg[k] = c;
                k++;
            }
            newArrImg[k] = '\n';
            k++;
        }
        return Arrays.toString(newArrImg);
    }

    public double getMaxRatio() {

        return maxRatio;
    }

    @Override
    public void setMaxWidth(int width) {
        this.width = width;

    }

    @Override
    public void setMaxHeight(int height) {
        this.height = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}
