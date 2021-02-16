package com.lsandoval9.springmedia.services.image;

import com.lsandoval9.springmedia.helpers.enums.RGB_COLORS;
import com.lsandoval9.springmedia.helpers.enums.basicImageFilters.BRIGHTNESS_IMAGE_VALUES;
import com.lsandoval9.springmedia.helpers.enums.basicImageFilters.SATURATION_IMAGE_VALUES;
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

                    red = imageHelpersService.checkColorRange(red);

                    color = new Color(
                            red,
                            color.getBlue(),
                            color.getGreen(),
                            color.getAlpha()
                    );

                } else if (filterColor.getColor().equalsIgnoreCase(RGB_COLORS.BLUE.getColor())) {

                    blue = color.getBlue() + (int) (((double) selectedValue / 100) * color.getBlue());

                    blue = imageHelpersService.checkColorRange(blue);

                    color = new Color(
                            color.getRed(),
                            color.getGreen(),
                            blue,
                            color.getAlpha()
                    );


                } else {

                    green = color.getGreen() + (int) (((double) selectedValue / 100) * color.getGreen());

                    green = imageHelpersService.checkColorRange(green);

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


    public byte[] brightness(MultipartFile file, String type,
                             BRIGHTNESS_IMAGE_VALUES selectedValue) throws IOException {

        this.byteArrayOutput = new ByteArrayOutputStream();

        BufferedImage image = imageHelpersService.getBufferedImage(file);

        Color color = null;

        double value = selectedValue.getPorcenteage();

        int red = 0,
                green = 0,
                blue = 0,
                result = 0;

        for (int horizontal = 0; horizontal < image.getWidth(); ++horizontal) {

            for (int vertical = 0; vertical < image.getHeight(); ++vertical) {


                color = new Color(image.getRGB(horizontal, vertical), true);

                red = (int) (color.getRed() + Math.round(value * color.getRed()));
                red = imageHelpersService.checkColorRange(red);

                green = (int) (color.getGreen() + Math.round(value * color.getGreen()));
                green = imageHelpersService.checkColorRange(green);

                blue = (int) (color.getBlue() + Math.round(value * color.getBlue()));
                blue = imageHelpersService.checkColorRange(blue);

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


    public byte[] saturation(MultipartFile file, String type,
                             SATURATION_IMAGE_VALUES value) throws IOException {

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
                resultRed = imageHelpersService.checkColorRange(resultRed);

                resultGreen = (int) (result + ((green) - result) * value.getValue());
                resultGreen = imageHelpersService.checkColorRange(resultGreen);

                resultBlue = (int) (result + ((blue) - result) * value.getValue());
                resultBlue = imageHelpersService.checkColorRange(resultBlue);


                color = new Color(resultRed, resultGreen, resultBlue, color.getAlpha());

                image.setRGB(horizontal, vertical, color.getRGB());

            }
        }


        ImageIO.write(image, type, byteArrayOutput);

        return byteArrayOutput.toByteArray();
    }
}
