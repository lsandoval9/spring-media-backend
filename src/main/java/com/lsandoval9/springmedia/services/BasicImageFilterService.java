package com.lsandoval9.springmedia.services;

import com.lsandoval9.springmedia.helpers.ImageHelpersService;
import com.lsandoval9.springmedia.helpers.enums.RGB_COLORS;
import com.lsandoval9.springmedia.helpers.enums.UNICOLOR_FILTER_VALUES;
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
}
