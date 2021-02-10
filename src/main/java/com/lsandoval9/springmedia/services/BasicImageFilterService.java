package com.lsandoval9.springmedia.services;

import com.lsandoval9.springmedia.helpers.ImageHelpersService;
import com.lsandoval9.springmedia.helpers.enums.RGB_COLORS;
import com.lsandoval9.springmedia.helpers.enums.basicImageFilters.ENHANCE_IMAGE_VALUE;
import com.lsandoval9.springmedia.helpers.enums.basicImageFilters.UNICOLOR_FILTER_VALUES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class BasicImageFilterService {

    ByteArrayOutputStream byteArrayOutput = null;

    private final ImageHelpersService imageHelpersService;

    private final Logger log = LoggerFactory.getLogger(BasicImageFilterService.class);

    @Autowired
    public BasicImageFilterService(ImageHelpersService imageHelpersService) {
        this.imageHelpersService = imageHelpersService;
    }


    public byte[] addUnicolorFilter(MultipartFile file, String type,
                                    UNICOLOR_FILTER_VALUES value, RGB_COLORS filterColor) throws IOException {

        this.byteArrayOutput = new ByteArrayOutputStream();

        BufferedImage image = imageHelpersService.getBufferedImage(file);

        if (image == null) throw new RuntimeException("NULL IMAGE");

        int selectedValue = value.getValue();

        int red,
                blue,
                green;

        Color color = null;

        for (int horizontal = 0; horizontal < image.getWidth(); ++horizontal) {

            for (int vertical = 0; vertical < image.getHeight(); ++vertical) {


                color = new Color(image.getRGB(horizontal, vertical), true);

                if (filterColor.getColor().equalsIgnoreCase(RGB_COLORS.RED.getColor())) {

                    red = color.getRed() + (int) (((double) selectedValue / 100) * color.getRed());

                    if (red > 255) red = 255;

                    color = new Color(
                            red,
                            color.getBlue(),
                            color.getGreen(),
                            color.getAlpha()
                    );

                } else if (filterColor.getColor().equalsIgnoreCase(RGB_COLORS.BLUE.getColor())) {

                    blue = color.getBlue() + (int) (((double) selectedValue / 100) * color.getBlue());

                    if (blue > 255) blue = 255;

                    color = new Color(
                            color.getRed(),
                            color.getGreen(),
                            blue,
                            color.getAlpha()
                    );


                } else {

                    green = color.getGreen() + (int) (((double) selectedValue / 100) * color.getGreen());

                    if (green > 255) green = 255;

                    color = new Color(
                            color.getRed(),
                            green,
                            color.getBlue(),
                            color.getAlpha()
                    );

                }

                image.setRGB(horizontal, vertical, color.getRGB());

            }

        }

        ImageIO.write(image, type, byteArrayOutput);

        return byteArrayOutput.toByteArray();

    }


    public byte[] brightness(MultipartFile file, String type) throws IOException {

        this.byteArrayOutput = new ByteArrayOutputStream();

        BufferedImage image = imageHelpersService.getBufferedImage(file);

        Color color = null;

        int red = 0,
                green = 0,
                blue = 0,
                min = 0,
                max = 0,
                delta = 0;

        for (int horizontal = 0; horizontal < image.getWidth(); ++horizontal) {

            for (int vertical = 0; vertical < image.getHeight(); ++vertical) {


                color = new Color(image.getRGB(horizontal, vertical), true);


                max = Math.max(
                        color.getRed(),
                        Math.max(
                                color.getBlue(),
                                color.getGreen()
                        ));

                min = Math.min(
                        color.getRed(),
                        Math.max(
                                color.getBlue(),
                                color.getGreen()
                        ));


                delta = max - min;

                red = color.getRed() + delta;
                if (red > 255) red = 255;

                blue = color.getBlue() + delta;
                if (blue > 255) blue = 255;

                green = color.getGreen() + delta;
                if (green > 255) green = 255;

                color = new Color(
                        red,
                        green,
                        blue,
                        color.getAlpha()
                );


                image.setRGB(horizontal, vertical, color.getRGB());
            }
        }

        ImageIO.write(image, type, byteArrayOutput);

        return byteArrayOutput.toByteArray();
    }


    public byte[] hue(MultipartFile file, String type) throws IOException {

        this.byteArrayOutput = new ByteArrayOutputStream();

        BufferedImage image = imageHelpersService.getBufferedImage(file);

        Color color = null;

        int red, green, blue, prom;

        for (int horizontal = 0; horizontal < image.getWidth(); ++horizontal) {

            for (int vertical = 0; vertical < image.getHeight(); ++vertical) {


                color = new Color(image.getRGB(horizontal, vertical), true);

                prom = (int) Math.round((double) (color.getRed() + color.getGreen() + color.getBlue()) / 3);

                red = color.getRed() - prom;
                if (red < 0) red = 0;

                green = color.getGreen() - prom;
                if (green < 0) green = 0;

                blue = color.getBlue() - prom;
                if (blue < 0) blue = 0;


                color = new Color(red, blue, green, color.getAlpha());

                image.setRGB(horizontal, vertical, color.getRGB());
            }
        }

        ImageIO.write(image, type, byteArrayOutput);

        return byteArrayOutput.toByteArray();
    }


    public byte[] saturation(MultipartFile file, String type,
                             ENHANCE_IMAGE_VALUE value) throws IOException {

        BufferedImage image = imageHelpersService.getBufferedImage(file);

        final double PR = 0.299;

        final double PG = 0.587;

        final double PB = 0.114;

        this.byteArrayOutput = new ByteArrayOutputStream();

        double red, green, blue, result;

        int resultRed, resultGreen, resultBlue;

        double aux;

        Color color;

        for (int horizontal = 0; horizontal < image.getWidth(); ++horizontal) {

            for (int vertical = 0; vertical < image.getHeight(); ++vertical) {

                color = new Color(image.getRGB(horizontal, vertical), true);

                red = (double) color.getRed();

                blue = (double) color.getBlue();

                green = (double) color.getGreen();

                result = Math.sqrt(
                        (red) * (red) * PR +
                                (green) * (green) * PG +
                                (blue) * (blue) * PB
                );

                resultRed = (int) (result + ((red) - result) * value.getValue());
                if (resultRed > 255) resultRed = 255;
                else if (resultRed < 0) resultRed = 0;

                resultGreen = (int) (result + ((green) - result) * value.getValue());
                if (resultGreen > 255) resultGreen = 255;
                else if (resultGreen < 0) resultGreen = 0;

                resultBlue = (int) (result + ((blue) - result) * value.getValue());
                if (resultBlue > 255) resultBlue = 255;
                else if (resultBlue < 0) resultBlue = 0;


                color = new Color(resultRed, resultGreen, resultBlue, color.getAlpha());

                image.setRGB(horizontal, vertical, color.getRGB());

            }
        }


        ImageIO.write(image, type, byteArrayOutput);

        return byteArrayOutput.toByteArray();
    }
}
