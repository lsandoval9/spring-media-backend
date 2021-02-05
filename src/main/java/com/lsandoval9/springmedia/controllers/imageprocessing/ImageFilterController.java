package com.lsandoval9.springmedia.controllers.imageprocessing;

import com.lsandoval9.springmedia.helpers.ImageHelpersService;
import com.lsandoval9.springmedia.services.BasicFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/f")
public class ImageFilterController {

    @Value("${user.rootfolder}")
    private String rootfolder;

    private final Logger log;

    private final ImageHelpersService imageHelpersService;

    private final BasicFilterService basicFilterService;

    @Autowired
    public ImageFilterController(@Qualifier("imageHelpersService") ImageHelpersService imageHelpersService,
                                 @Qualifier("basicFilterService") BasicFilterService basicFilterService) {


        this.basicFilterService = basicFilterService;
        this.log = LoggerFactory.getLogger(ImageFilterController.class);
        this.imageHelpersService = imageHelpersService;
    }

    // FILTERS

    @PostMapping(value = "/sepia", produces = {"image/png", "image/jpeg", "image/webp"})
    public byte[] addSepiaFilter(@RequestBody MultipartFile file) throws IOException {

        String imageType = imageHelpersService.getImageType(file);

        log.error(imageType);

        imageHelpersService.isFileValid(file, imageType);

        return basicFilterService.addSepiaFilter(file, imageHelpersService.getExtension(imageType));

    }


    @PostMapping(path = "/reflect",  produces = {"image/png", "image/jpeg", "image/webp"})
    public byte[] addReflectFilter(@RequestBody MultipartFile file) throws IOException {

        String imageType = imageHelpersService.getImageType(file);

        log.error(imageType);

        imageHelpersService.isFileValid(file, imageType);

        return basicFilterService.addReflectFilter(file, imageHelpersService.getExtension(imageType));

    }


    @PostMapping(path = "/grayscale", produces = {"image/png", "image/jpeg", "image/webp"})
    public byte[] addGrayscaleFilter(@RequestBody MultipartFile file) throws IOException {

        String imageType = imageHelpersService.getImageType(file);

        imageHelpersService.isFileValid(file, imageType);

        return basicFilterService.addGrayscaleFilter(file, imageHelpersService.getExtension(imageType));

    }


    @PostMapping(path = "/blur", produces = {"image/png", "image/jpeg", "image/webp"})
    public byte[] addBlurFilter(@RequestBody MultipartFile file) throws IOException {

        String imageType = imageHelpersService.getImageType(file);

        imageHelpersService.isFileValid(file, imageType);

        return basicFilterService.addBlurFilter(file, imageHelpersService.getExtension(imageType));

    }

}
