package com.lsandoval9.springmedia.controllers.fileProcessing;

import com.lsandoval9.springmedia.dto.fileProcessing.FileResponseDto;
import com.lsandoval9.springmedia.services.file.FileService;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/fi")
public class FileProcessingController {

    private final FileService fileService;

    @Autowired
    public FileProcessingController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/detect")
    public FileResponseDto detectType(@RequestParam(name = "file") MultipartFile file) throws IOException, MimeTypeException {


        String mimetype = fileService.getMimetype(file);
        String extension = fileService.getExtension(mimetype);


        if (fileService.isValidFile(file)) {

            return new FileResponseDto(
                    mimetype,
                    extension
            );

        }

        throw new IllegalStateException("Unkown error");


    }

}
