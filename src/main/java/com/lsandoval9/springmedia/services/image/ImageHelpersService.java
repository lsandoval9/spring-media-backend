package com.lsandoval9.springmedia.services.image;

import com.lsandoval9.springmedia.helpers.enums.SUPPORTED_IMAGE_TYPES;
import com.lsandoval9.springmedia.services.file.FileService;
import net.coobird.thumbnailator.makers.FixedSizeThumbnailMaker;
import net.coobird.thumbnailator.resizers.DefaultResizerFactory;
import net.coobird.thumbnailator.resizers.Resizer;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.Objects;


@Service
public class ImageHelpersService {


    private final Tika tika;

    private final FileService fileService;

    @Autowired
    public ImageHelpersService(Tika tika, FileService fileService) {
        this.tika = tika;
        this.fileService = fileService;
    }

    @Deprecated
    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);

    }


    public String getImageType(MultipartFile file) throws IOException {

        byte[] bytes = parseBytes(file);

        String type = this.tika.detect(bytes);

        return type;

    }


    public byte[] parseBytes(MultipartFile file) throws IOException {

        return file.getBytes();

    }

    private boolean isSupportedMimeType(String mimetype) {

        boolean match = false;

        for (SUPPORTED_IMAGE_TYPES type : SUPPORTED_IMAGE_TYPES.values()) {

            if (Objects.equals(mimetype, type.getTypeName())) {

                match = true;

                break;
            }

        }


        return match;

    }


    public String getExtension(String mimetype) {

        return mimetype.split("/")[1];

    }


    /**
     * @param file      image as a multipartfile
     * @param imageType String containing the mimetype of the image
     * @return boolean if the image is valid and not null
     */
    public boolean isFileValid(MultipartFile file, String imageType) {


        if (file.isEmpty()) {

            return false;

        }

        return isSupportedMimeType(imageType);

    }


    public BufferedImage getBufferedImage(MultipartFile file) throws IOException {

        return ImageIO.read(file.getInputStream());

    }


    public int checkColorRange(int rgbColor) {

        if (rgbColor > 255) {

            return 255;
        }

        return Math.max(rgbColor, 0);

    }


    /**
     * @param image - image to be resized with certain ratio
     * @return resized image
     * @author - lsandoval9
     */
    public BufferedImage resizeImage(BufferedImage image) {

        int newWidth = image.getWidth();
        int newHeight = image.getHeight();

        int ratio = 850;

        while (newWidth > ratio || newHeight > ratio) {

            if (newWidth > ratio) {

                newWidth = newWidth - 100;

            }

            if (newHeight > ratio) {

                newHeight = newHeight - 100;

            }

        }

        Resizer resizer = DefaultResizerFactory.getInstance().getResizer(
                new Dimension(image.getWidth(), image.getHeight()),
                new Dimension(newWidth, newHeight));
        BufferedImage scaledImage = new FixedSizeThumbnailMaker(
                newWidth, newHeight, true, true
        )
                .resizer(resizer)
                .make(image);

        return scaledImage;

    }
}
