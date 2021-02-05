package com.lsandoval9.springmedia.helpers;

import com.lsandoval9.springmedia.helpers.enums.SupportedImageTypes;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.Objects;


@Service
public class ImageHelpersService {


    private final Tika tika;

    @Autowired
    public ImageHelpersService(Tika tika) {
        this.tika = tika;
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

    public boolean isSupportedMimeType(String mimetype) {

        boolean match = false;

        for (SupportedImageTypes type : SupportedImageTypes.values()) {

            if (Objects.equals(mimetype, type.getTypeName())) {

                match = true;

            }

        }


        return match;

    }


    public String getExtension(String mimetype) {

        return mimetype.split("/")[1];

    }


    public void isFileValid(MultipartFile file, String imageType) {

        if (file.isEmpty()) {

            throw new IllegalStateException("FILE IS EMPTY");

        }

        if (!isSupportedMimeType(imageType)) {


            throw new IllegalStateException("MIMETYPE NOT SUPPORTED");

        }

    }
}
