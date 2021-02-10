package com.lsandoval9.springmedia.dto;

import com.lsandoval9.springmedia.helpers.enums.RGB_COLORS;
import com.lsandoval9.springmedia.helpers.enums.basicImageFilters.UNICOLOR_FILTER_VALUES;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class UnicolorFilterDto {

    @NotNull
    private final MultipartFile file;

    @NotNull
    private final UNICOLOR_FILTER_VALUES unicolorFIlterValues;

    @NotNull
    private final RGB_COLORS rgbCOLORS;

    public UnicolorFilterDto(MultipartFile file, UNICOLOR_FILTER_VALUES unicolorFIlterValues, RGB_COLORS rgbCOLORS) {
        this.file = file;
        this.unicolorFIlterValues = unicolorFIlterValues;
        this.rgbCOLORS = rgbCOLORS;
    }

    public MultipartFile getFile() {
        return file;
    }

    public UNICOLOR_FILTER_VALUES getUnicolorFIlterValues() {
        return unicolorFIlterValues;
    }

    public RGB_COLORS getRgbColors() {
        return rgbCOLORS;
    }
}
