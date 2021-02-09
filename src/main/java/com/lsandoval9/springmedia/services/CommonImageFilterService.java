package com.lsandoval9.springmedia.services;

import com.lsandoval9.springmedia.helpers.ImageHelpersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class CommonImageFilterService {

    ByteArrayOutputStream byteArrayOutput = null;

    private final ImageHelpersService imageHelpersService;

    @Autowired
    public CommonImageFilterService(ImageHelpersService imageHelpersService) {
        this.imageHelpersService = imageHelpersService;
    }


    public byte[] addSepiaFilter(MultipartFile file, String type) throws IOException {

        BufferedImage image = imageHelpersService.getBufferedImage(file);

        Color color;

        this.byteArrayOutput = new ByteArrayOutputStream();

        for (int horizontal = 0; horizontal < image.getWidth() - 1; ++horizontal) {

            for (int vertical = 0; vertical < image.getHeight() - 1; ++vertical) {

                color = new Color(image.getRGB(horizontal, vertical), true);

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

                image.setRGB(horizontal, vertical, color.getRGB());

            }

        }

        ImageIO.write(image, type, byteArrayOutput);

        return byteArrayOutput.toByteArray();

    }


    public byte[] addReflectFilter(MultipartFile file, String type) throws IOException {

        BufferedImage image = imageHelpersService.getBufferedImage(file);

        this.byteArrayOutput = new ByteArrayOutputStream();

        int aux;

        Color color;

        for (int horizontal = 0; horizontal < (Math.round((float) image.getWidth() - 1) / 2); ++horizontal) {

            for (int vertical = 0; vertical < image.getHeight() - 1; ++vertical) {


                aux = image.getRGB(image.getWidth() - horizontal - 1, vertical);

                color = new Color(aux, true);

                image.setRGB(
                        ((image.getWidth() - 1) - horizontal),
                        vertical,
                        image.getRGB(horizontal, vertical)
                );

                image.setRGB(horizontal, vertical, color.getRGB());
            }
        }


        ImageIO.write(image, type, byteArrayOutput);

        return byteArrayOutput.toByteArray();


    }


    public byte[] addGrayscaleFilter(MultipartFile file, String type) throws IOException {

        Color color;

        BufferedImage image = imageHelpersService.getBufferedImage(file);

        this.byteArrayOutput = new ByteArrayOutputStream();

        for (int horizontal = 0; horizontal < image.getWidth() - 1; horizontal++) {

            for (int vertical = 0; vertical < image.getHeight() - 1; vertical++) {

                color = new Color(image.getRGB(horizontal, vertical), true);

                int prom = Math.round(
                        (float) (
                                (color.getRed() +
                                        color.getGreen() +
                                        color.getBlue()) / 3)
                );

                color = new Color(prom, prom, prom, color.getAlpha());

                image.setRGB(horizontal, vertical, color.getRGB());

            }
        }

        ImageIO.write(image, type, byteArrayOutput);

        return byteArrayOutput.toByteArray();


    }


    public byte[] addBlurFilter(MultipartFile file, String type) throws IOException {

        BufferedImage image = imageHelpersService.getBufferedImage(file);

        this.byteArrayOutput = new ByteArrayOutputStream();

        int red, green, blue, alpha;

        int totalSquares;

        Color color;


        for (int horizontal = 0; horizontal < image.getWidth(); ++horizontal) {

            for (int vertical = 0; vertical < image.getHeight(); ++vertical) {


                color = new Color(image.getRGB(horizontal, vertical), true);


                red = color.getRed();

                blue = color.getBlue();

                green = color.getGreen();

                alpha = color.getAlpha();

                totalSquares = 1;

                if (vertical != 0) { //LOOK UP

                    color = new Color(image.getRGB(horizontal, vertical - 1), true);

                    red += color.getRed();

                    blue += color.getBlue();

                    green += color.getGreen();

                    totalSquares++;

                    if (horizontal != image.getWidth() - 1)  //LOOK UP - RIGHT
                    {

                        color = new Color(image.getRGB(horizontal + 1, vertical - 1), true);

                        red += color.getRed();

                        blue += color.getBlue();

                        green += color.getGreen();

                        totalSquares++;
                    }

                    if (horizontal != 0) // LOOK UP - LEFT
                    {
                        color = new Color(image.getRGB(horizontal - 1, vertical - 1), true);

                        red += color.getRed();

                        blue += color.getBlue();

                        green += color.getGreen();

                        totalSquares++;
                    }
                }


                if (horizontal != image.getWidth() - 1) { // LOOK TO THE RIGHT

                    color = new Color(image.getRGB(horizontal + 1, vertical), true);

                    red += color.getRed();

                    blue += color.getBlue();

                    green += color.getGreen();

                    totalSquares++;
                }

                if (horizontal != 0) { //LOOK TO THE LEFT

                    color = new Color(image.getRGB(horizontal - 1, vertical), true);

                    red += color.getRed();

                    blue += color.getBlue();

                    green += color.getGreen();

                    totalSquares++;
                }

                if (vertical != (image.getHeight() - 1)) { //LOOK DOWN

                    color = new Color(image.getRGB(horizontal, vertical + 1), true);

                    red += color.getRed();

                    blue += color.getBlue();

                    green += color.getGreen();

                    totalSquares++;

                    if (horizontal != image.getWidth() - 1) //LOOK DOWN - RIGHT
                    {
                        color = new Color(image.getRGB(horizontal + 1, vertical + 1), true);

                        red += color.getRed();

                        blue += color.getBlue();

                        green += color.getGreen();

                        totalSquares++;
                    }

                    if (horizontal != 0) //LOOK DOWN - LEFT
                    {
                        color = new Color(image.getRGB(horizontal - 1, vertical + 1), true);

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

                image.setRGB(horizontal, vertical, color.getRGB());
            }
        }

        ImageIO.write(image, type, byteArrayOutput);

        return byteArrayOutput.toByteArray();

    }

}
