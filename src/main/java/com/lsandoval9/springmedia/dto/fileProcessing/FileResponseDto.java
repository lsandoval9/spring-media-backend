package com.lsandoval9.springmedia.dto.fileProcessing;

public class FileResponseDto {

    private final String mimetype;

    private final String extension;

    public FileResponseDto(String mimetype) {
        this.mimetype = mimetype;
        this.extension = null;
    }

    public FileResponseDto(String mimetype, String extension) {
        this.mimetype = mimetype;
        this.extension = extension;
    }

    public String getMimetype() {
        return mimetype;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return "FileResponseDto{" +
                "mimetype='" + mimetype + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }

}
