package com.lsandoval9.springmedia.controllers.imageprocessing;

import com.lsandoval9.springmedia.services.image.AsciiImageFilterService;
import com.lsandoval9.springmedia.services.image.ImageHelpersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping(path = "/fi")
public class AsciiImageFilterController {

    private final ImageHelpersService imageHelpersService;

    private final AsciiImageFilterService asciiImageFilterService;

    @Autowired
    public AsciiImageFilterController(ImageHelpersService imageHelpersService,
                                      AsciiImageFilterService asciiImageFilterService) {
        this.imageHelpersService = imageHelpersService;
        this.asciiImageFilterService = asciiImageFilterService;
    }


    @PostMapping(path = "/ascii", produces = {"image/jpeg"})
    public byte[] parseToAscii(@RequestParam(name = "file") MultipartFile file,
                               @RequestParam(name = "negative") boolean negative) throws IOException {

        String imageType = imageHelpersService.getImageType(file);


        if (imageHelpersService.isFileValid(file, imageType)) {

            BufferedImage image = imageHelpersService.getBufferedImage(file);

            String extension = imageHelpersService.getExtension(imageType);

            byte[] convert = asciiImageFilterService.convertToAsciiImage(image, negative, extension);

            return convert;
        }

        throw new RuntimeException("Error ocurred, file invalid");
    }


    @PostMapping(path = "/ascii-text", produces = {"text/plain"})
    public String parseToAsciiText(@RequestParam(name = "file") MultipartFile file,
                               @RequestParam(name = "negative") boolean negative) throws IOException {

        String imageType = imageHelpersService.getImageType(file);


        if (imageHelpersService.isFileValid(file, imageType)) {

            BufferedImage image = imageHelpersService.getBufferedImage(file);

            String extension = imageHelpersService.getExtension(imageType);

            String convert = asciiImageFilterService.convertToAsciiText(image, negative);

            return convert;
        }

        throw new RuntimeException("Error ocurred, file invalid");
    }

}
