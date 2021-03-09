package com.lsandoval9.springmedia.controllers.imageprocessing;

import com.lsandoval9.springmedia.exceptions.InvalidFileException;
import com.lsandoval9.springmedia.helpers.enums.SUPPORTED_IMAGE_TYPES;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Copyright (c) 2011 Aravind Rao
Modifications by Sam Barnum, 360Works 2012
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 Original repo - https://gist.github.com/shmert/3859200

 Some modifications to the original code were made

*/
@RestController
@RequestMapping(path = "/im")
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

	// TODO: make negative optional


        if (imageHelpersService.isFileValid(file, imageType)) {

            BufferedImage image = imageHelpersService.getBufferedImage(file);

            String extension = imageHelpersService.getExtension(imageType);

            byte[] convert = asciiImageFilterService.convertToAsciiImage(image, negative, extension);

            return convert;
        }

        List<String > enumConstants = Stream.of(SUPPORTED_IMAGE_TYPES.values())
                .map(SUPPORTED_IMAGE_TYPES::getTypeName)
                .collect(Collectors.toList());

        throw new InvalidFileException("Error ocurred, file invalid", enumConstants);
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


        List<String > enumConstants = Stream.of(SUPPORTED_IMAGE_TYPES.values())
                .map(SUPPORTED_IMAGE_TYPES::getTypeName)
                .collect(Collectors.toList());


        throw new InvalidFileException("Error ocurred, file invalid",
                enumConstants);
    }

}
