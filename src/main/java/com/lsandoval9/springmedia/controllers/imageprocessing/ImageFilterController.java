package com.lsandoval9.springmedia.controllers.imageprocessing;

import com.lsandoval9.springmedia.services.image.ImageHelpersService;
import com.lsandoval9.springmedia.helpers.enums.RGB_COLORS;
import com.lsandoval9.springmedia.helpers.enums.basicImageFilters.BRIGHTNESS_IMAGE_VALUES;
import com.lsandoval9.springmedia.helpers.enums.basicImageFilters.SATURATION_IMAGE_VALUES;
import com.lsandoval9.springmedia.helpers.enums.basicImageFilters.UNICOLOR_FILTER_VALUES;
import com.lsandoval9.springmedia.services.image.CommonImageFilterService;
import com.lsandoval9.springmedia.services.image.BasicImageFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/im")
public class ImageFilterController {

    @Value("${user.rootfolder}")
    private String rootfolder;

    private final Logger log;

    private final ImageHelpersService imageHelpersService;

    private final CommonImageFilterService commonImageFilterService;

    private final BasicImageFilterService basicImageFilterService;

    @Autowired
    public ImageFilterController(@Qualifier("imageHelpersService") ImageHelpersService imageHelpersService,
                                 @Qualifier("commonImageFilterService") CommonImageFilterService commonImageFilterService,
                                 BasicImageFilterService basicImageFilterService) {


        this.commonImageFilterService = commonImageFilterService;
        this.basicImageFilterService = basicImageFilterService;
        this.log = LoggerFactory.getLogger(ImageFilterController.class);
        this.imageHelpersService = imageHelpersService;
    }

    // FILTERS

    @PostMapping(value = "/sepia", produces = {"image/png", "image/jpeg", "image/webp"})
    public byte[] addSepiaFilter(@RequestBody MultipartFile file) throws IOException {

        String imageType = imageHelpersService.getImageType(file);

        log.error(imageType);

        imageHelpersService.isFileValid(file, imageType);

        return commonImageFilterService.addSepiaFilter(file, imageHelpersService.getExtension(imageType));

    }


    @PostMapping(path = "/reflect", produces = {"image/png", "image/jpeg", "image/webp"})
    public byte[] addReflectFilter(@RequestBody MultipartFile file) throws IOException {

        String imageType = imageHelpersService.getImageType(file);

        imageHelpersService.isFileValid(file, imageType);

        return commonImageFilterService.addReflectFilter(file, imageHelpersService.getExtension(imageType));

    }


    @PostMapping(path = "/grayscale", produces = {"image/png", "image/jpeg", "image/webp"})
    public byte[] addGrayscaleFilter(@RequestBody MultipartFile file) throws IOException {

        String imageType = imageHelpersService.getImageType(file);

        imageHelpersService.isFileValid(file, imageType);

        return commonImageFilterService.addGrayscaleFilter(file, imageHelpersService.getExtension(imageType));

    }


    @PostMapping(path = "/blur", produces = {"image/png", "image/jpeg", "image/webp"})
    public byte[] addBlurFilter(@RequestBody MultipartFile file) throws IOException {

        String imageType = imageHelpersService.getImageType(file);

        imageHelpersService.isFileValid(file, imageType);

        return commonImageFilterService.addBlurFilter(file, imageHelpersService.getExtension(imageType));

    }


    @PostMapping(path = "/unicolor", produces = {"image/png", "image/jpeg", "image/webp"})
    public byte[] unicolorImageFilter(@RequestParam(name = "file") MultipartFile file,
                                      @RequestParam(name = "value") UNICOLOR_FILTER_VALUES filterValue,
                                      @RequestParam(name = "color") RGB_COLORS color) throws IOException {

        String mimetype = imageHelpersService.getImageType(file);


        imageHelpersService.isFileValid(file, mimetype);

        byte[] bytesImage = basicImageFilterService.addUnicolorFilter(file,
                imageHelpersService.getExtension(mimetype),
                filterValue,
                color
        );


        return bytesImage;
    }

    @PostMapping(path = "/brightness", produces = {"image/png", "image/jpeg", "image/webp"})
    public byte[] brightness(@RequestParam(name = "file") MultipartFile file,
                             @RequestParam(name = "value") BRIGHTNESS_IMAGE_VALUES selectedValue) throws IOException {

        String mimetype = imageHelpersService.getImageType(file);


        imageHelpersService.isFileValid(file, mimetype);

        byte[] bytesImage = basicImageFilterService.brightness(file,
                imageHelpersService.getExtension(mimetype),
                selectedValue
        );


        return bytesImage;
    }


    @PostMapping(path = "/negative", produces = {"image/png", "image/jpeg", "image/webp"})
    public byte[] hue(@RequestParam(name = "file") MultipartFile file) throws IOException {

        String mimetype = imageHelpersService.getImageType(file);


        imageHelpersService.isFileValid(file, mimetype);

        byte[] bytesImage = commonImageFilterService.negative(file,
                imageHelpersService.getExtension(mimetype)
        );


        return bytesImage;
    }


    @PostMapping(path = "/saturation", produces = {"image/png", "image/jpeg", "image/webp"})
    public byte[] saturation(@RequestParam(name = "file") MultipartFile file,
                             @RequestParam(name = "value") SATURATION_IMAGE_VALUES value) throws IOException {

        String mimetype = imageHelpersService.getImageType(file);


        imageHelpersService.isFileValid(file, mimetype);

        byte[] bytesImage = basicImageFilterService.saturation(file,
                imageHelpersService.getExtension(mimetype),
                value
        );


        return bytesImage;
    }
}
