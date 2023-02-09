package com.epam.datastorage.service;

import com.epam.datastorage.dao.FileEntity;
import com.epam.datastorage.dto.FileDto;

import java.util.Objects;

public class FileConverter {
    public static FileEntity toFileEntity(FileDto fileDto){

        FileEntity fileEntity = new FileEntity();
        if (!Objects.nonNull(fileDto.getId()) ) {
            fileEntity.setId(fileDto.getId());
        }
        fileEntity.setFileName(fileDto.getFileName());
        fileEntity.setContent(fileDto.getContent());
        return fileEntity;
    }

    public static FileDto toFileDto(FileEntity fileEntity){

        FileDto fileDto = new FileDto();
        if (fileEntity.getId() != null || fileEntity.getId() != 0) {
            fileDto.setId(fileDto.getId());
        }
        fileDto.setFileName(fileEntity.getFileName());
        fileDto.setContent(fileEntity.getContent());
        return fileDto;
    }

}
