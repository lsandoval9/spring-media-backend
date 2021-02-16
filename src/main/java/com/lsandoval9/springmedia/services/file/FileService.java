package com.lsandoval9.springmedia.services.file;

import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service()
public class FileService {

    private final Tika tika;

    @Autowired
    public FileService(@Qualifier("tika") Tika tika) {
        this.tika = tika;
    }


    public String getMimetype(MultipartFile file) throws IOException, MimeTypeException {

        return tika.detect(file.getBytes());

    }


    public String getExtension(String mimetype) throws MimeTypeException {

        TikaConfig config = TikaConfig.getDefaultConfig();

        /*Metadata metadata = new Metadata();
        InputStream stream = TikaInputStream.get(file.getBytes());
        MediaType mediaType = config.getMimeRepository().detect(stream, metadata);*/

        // Fest the most common extension for the detected type
        MimeType mimeType = config.getMimeRepository().forName(mimetype);
        String extension = mimeType.getExtension();


        return extension;
    }
}
