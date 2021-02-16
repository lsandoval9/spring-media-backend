package com.lsandoval9.springmedia.services.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class AsciiImageFilterService {

    private final ImageHelpersService imageHelpersService;

    private ByteArrayOutputStream byteArrayOutput;

    @Autowired
    public AsciiImageFilterService(ImageHelpersService imageHelpersService) {

        this.imageHelpersService = imageHelpersService;
        ;
    }


    public byte[] convertToAsciiImage(final BufferedImage image, boolean negative,
                                      String extension) throws IOException {


        StringBuilder sb = parseToStringBuilder(image, negative);

        String[] text = sb.toString().split("\n");


        byte[] bytes = drawString(text, image.getWidth(), image.getHeight(),
                "png", true);

        return bytes;

    }


    public String convertToAsciiText(final BufferedImage image, boolean negative) throws IOException {


        StringBuilder sb = parseToStringBuilder(image, negative);

        String text = sb.toString();

        return text;

    }


    public StringBuilder parseToStringBuilder(BufferedImage image, boolean negative) {

        int width = image.getWidth();
        int height = image.getHeight();
        byte[] bytes = new byte[0];
        StringBuilder sb = new StringBuilder((width + 1) * height);

        for (int y = 0; y < height; y++) {

            if (sb.length() != 0) sb.append("\n");

            for (int x = 0; x < width; x++) {

                Color pixelColor = new Color(image.getRGB(x, y));
                double gValue = (double) pixelColor.getRed() * 0.2989 + (double) pixelColor.getBlue() * 0.5870 + (double) pixelColor.getGreen() * 0.1140;
                final char s = negative ? returnStrNeg(gValue) : returnStrPos(gValue);
                sb.append(s);

            }

        }

        return sb;
    }


    private byte[] drawString(String[] text, int width,
                              int height, String extension, boolean asText) throws IOException {

        this.byteArrayOutput = new ByteArrayOutputStream();

        ImageOutputStream outputStream = new MemoryCacheImageOutputStream(byteArrayOutput);

        int y = 0;
        Font font = new Font("Monospaced", Font.PLAIN, 10);


        BufferedImage bufferedImage = new BufferedImage(width * 6, height * 7,
                BufferedImage.TYPE_USHORT_555_RGB);

        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, width, height);
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        for (String line : text) {

            graphics.drawString(line, 0, y);

            y += graphics.getFontMetrics().getHeight() - 6;
        }

        ImageIO.write(bufferedImage, extension, byteArrayOutput);

        return byteArrayOutput.toByteArray();

    }

    private char returnStrPos(double g)//takes the grayscale value as parameter
    {
        final char str;

        if (g >= 230.0) {
            str = ' ';
        } else if (g >= 200.0) {
            str = '.';
        } else if (g >= 180.0) {
            str = '*';
        } else if (g >= 160.0) {
            str = ':';
        } else if (g >= 130.0) {
            str = 'o';
        } else if (g >= 100.0) {
            str = '&';
        } else if (g >= 70.0) {
            str = '8';
        } else if (g >= 50.0) {
            str = '#';
        } else {
            str = '@';
        }
        return str; // return the character

    }

    /**
     * Same method as above, except it reverses the darkness of the pixel. A dark pixel is given a light character and vice versa.
     *
     * @param g grayscale
     * @return char
     */
    private char returnStrNeg(double g) {
        final char str;

        if (g >= 230.0) {
            str = '@';
        } else if (g >= 200.0) {
            str = '#';
        } else if (g >= 180.0) {
            str = '8';
        } else if (g >= 160.0) {
            str = '&';
        } else if (g >= 130.0) {
            str = 'o';
        } else if (g >= 100.0) {
            str = ':';
        } else if (g >= 70.0) {
            str = '*';
        } else if (g >= 50.0) {
            str = '.';
        } else {
            str = ' ';
        }
        return str;

    }


    public byte[] getImageAsJpg(ByteArrayOutputStream output, BufferedImage image) throws IOException {


        try (ImageOutputStream outputStream = ImageIO.createImageOutputStream(output)) {

            // NOTE: The rest of the code is just a cleaned up version of your code

            // Obtain writer for JPEG format
            ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("JPEG").next();

            // Configure JPEG compression: 70% quality
            ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
            jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpgWriteParam.setCompressionQuality(0.1f);

            // Set your in-memory stream as the output
            jpgWriter.setOutput(outputStream);

            // Write image as JPEG w/configured settings to the in-memory stream
            // (the IIOImage is just an aggregator object, allowing you to associate
            // thumbnails and metadata to the image, it "does" nothing)
            jpgWriter.write(null, new IIOImage(image, null, null), jpgWriteParam);

            // Dispose the writer to free resources
            jpgWriter.dispose();
        }

        byte[] jpegData = output.toByteArray();

        return jpegData;
    }


}
