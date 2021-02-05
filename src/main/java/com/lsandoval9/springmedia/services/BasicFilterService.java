package com.lsandoval9.springmedia.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class BasicFilterService {

    ByteArrayOutputStream byteArrayOutput = null;


    public byte[] addSepiaFilter(MultipartFile file, String type) throws IOException {

        BufferedImage image = getBufferedImage(file);

        Color color;

        this.byteArrayOutput = new ByteArrayOutputStream();

        for (int i = 0; i < image.getWidth() - 1; ++i) {

            for (int j = 0; j < image.getHeight() - 1; ++j) {

                color = new Color(image.getRGB(i, j), true);

                int sepiaRed = (int) Math.floor(
                        (0.393 * color.getRed())
                                + (0.769 * color.getGreen())
                                + (0.189 * color.getBlue())
                );

                if (sepiaRed > 255) sepiaRed = 255;

                int sepiaGreen = (int) Math.floor(
                        (0.349 * color.getRed())
                                + (0.686 * color.getGreen())
                                + (0.168 * color.getBlue())
                );

                if (sepiaGreen > 255) sepiaGreen = 255;

                int sepiaBlue = (int) Math.floor(
                        (0.272 * color.getRed())
                                + (0.534 * color.getGreen())
                                + (0.131 * color.getBlue())
                );

                if (sepiaBlue > 255) sepiaBlue = 255;

                color = new Color(sepiaRed, sepiaGreen, sepiaBlue, color.getAlpha());

                image.setRGB(i, j, color.getRGB());

            }

        }

        ImageIO.write(image, type, byteArrayOutput);

        return byteArrayOutput.toByteArray();

    }


    public byte[] addReflectFilter(MultipartFile file, String type) throws IOException {

        BufferedImage image = getBufferedImage(file);

        this.byteArrayOutput = new ByteArrayOutputStream();

        int aux;

        Color color;

        for (int i = 0; i < (Math.round((float) image.getWidth() - 1) / 2); ++i) {

            for (int j = 0; j < image.getHeight() - 1; ++j) {


                aux = image.getRGB(image.getWidth() - i - 1, j);

                color = new Color(aux, true);

                image.setRGB(((image.getWidth() - 1) - i), j, image.getRGB(i, j));

                image.setRGB(i, j, color.getRGB());
            }
        }



        ImageIO.write(image, type, byteArrayOutput);

        return byteArrayOutput.toByteArray();


    }


    public byte[] addGrayscaleFilter(MultipartFile file, String type) throws IOException {

        Color color;

        BufferedImage image = getBufferedImage(file);

        this.byteArrayOutput = new ByteArrayOutputStream();

        for (int i = 0; i < image.getWidth() - 1; i++) {

            for (int j = 0; j < image.getHeight() - 1; j++) {

                color = new Color(image.getRGB(i, j), true);

                int prom = Math.round(
                        (float) (
                                (color.getRed() +
                                        color.getGreen() +
                                        color.getBlue()) / 3)
                );

                color = new Color(prom, prom, prom, color.getAlpha());

                image.setRGB(i, j, color.getRGB());

            }
        }

        ImageIO.write(image, type, byteArrayOutput);

        return byteArrayOutput.toByteArray();


    }


    public byte[] addBlurFilter(MultipartFile file, String type) throws IOException {

        BufferedImage image = getBufferedImage(file);

        this.byteArrayOutput = new ByteArrayOutputStream();

        int red, green, blue, alpha;

        int totalSquares;

        Color color;



        for (int i = 0; i < image.getWidth(); i++) {

            for (int j = 0; j < image.getHeight(); j++) {


                color = new Color(image.getRGB(i, j), true);


                red = color.getRed();

                blue = color.getBlue();

                green = color.getGreen();

                alpha = color.getAlpha();

                totalSquares = 1;

                if (j != 0) { //LOOK UP

                    color = new Color(image.getRGB(i, j - 1), true);

                    red += color.getRed();

                    blue += color.getBlue();

                    green += color.getGreen();

                    totalSquares++;

                    if (i != image.getWidth() - 1)  //LOOK UP - RIGHT
                    {

                        color = new Color(image.getRGB(i + 1, j - 1), true);

                        red += color.getRed();

                        blue += color.getBlue();

                        green += color.getGreen();

                        totalSquares++;
                    }

                    if (i != 0) // LOOK UP - LEFT
                    {
                        color = new Color(image.getRGB(i - 1, j - 1), true);

                        red += color.getRed();

                        blue += color.getBlue();

                        green += color.getGreen();

                        totalSquares++;
                    }
                }


                if (i != image.getWidth() - 1) { // LOOK TO THE RIGHT

                    color = new Color(image.getRGB(i + 1, j), true);

                    red += color.getRed();

                    blue += color.getBlue();

                    green += color.getGreen();

                    totalSquares++;
                }

                if (i != 0) { //LOOK TO THE LEFT

                    color = new Color(image.getRGB(i - 1, j), true);

                    red += color.getRed();

                    blue += color.getBlue();

                    green += color.getGreen();

                    totalSquares++;
                }

                if (j != (image.getHeight() - 1)) { //LOOK DOWN

                    color = new Color(image.getRGB(i, j + 1), true);

                    red += color.getRed();

                    blue += color.getBlue();

                    green += color.getGreen();

                    totalSquares++;

                    if (i != image.getWidth() - 1) //LOOK DOWN - RIGHT
                    {
                        color = new Color(image.getRGB(i + 1, j + 1), true);

                        red += color.getRed();

                        blue += color.getBlue();

                        green += color.getGreen();

                        totalSquares++;
                    }

                    if (i != 0) //LOOK DOWN - LEFT
                    {
                        color = new Color(image.getRGB(i - 1, j + 1), true);

                        red += color.getRed();

                        blue += color.getBlue();

                        green += color.getGreen();


                        totalSquares++;
                    }
                }

                color = new Color(
                        (Math.round((float) red / totalSquares)),
                        (Math.round((float) green / totalSquares)),
                        (Math.round((float) blue / totalSquares)),
                        alpha);

                image.setRGB(i, j, color.getRGB());
            }
        }

        ImageIO.write(image, type, byteArrayOutput);

        return byteArrayOutput.toByteArray();

    }


    public BufferedImage getBufferedImage(MultipartFile file) throws IOException {

        return ImageIO.read(file.getInputStream());

    }


}
