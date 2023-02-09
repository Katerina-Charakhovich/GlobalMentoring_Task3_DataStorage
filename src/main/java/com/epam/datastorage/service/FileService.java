package com.epam.datastorage.service;

import com.epam.datastorage.dao.FileEntity;
import com.epam.datastorage.dao.FileRepository;
import com.epam.datastorage.dto.FileDto;
import com.epam.datastorage.service.exception.PathException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;
    FileConverter fileConverter = new FileConverter();

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void upload(FileDto fileDto) {
        Map<String, Object> save = fileRepository.save(FileConverter.toFileEntity(fileDto));
    }

    public boolean download(Integer id, String path) {
        Optional<FileEntity> fileEntity = fileRepository.findById(id);
        return fileEntity.isPresent() ? writeFileWithBuffer(FileConverter.toFileDto(fileEntity.get()),path) : null;
    }

    public boolean writeFileWithBuffer(FileDto fileDto, String path) {
        int validatePathCopyTo = isFileOrDirectory(path);
        if (validatePathCopyTo != -1) {
            if (validatePathCopyTo == 1) {
                String newPath = path + "/" + fileDto.getFileName();
                File file = new File(newPath);
                path = newPath;
            }
        }
        return moveWithFileStreamsWithBuffer(fileDto,path);
    }

    public int isFileOrDirectory(String path) {
        File f = new File(path);
        if (f.exists()) {
            return Files.isDirectory(f.toPath()) ? 1 : 0;
        } else return -1;
    }

    public boolean moveWithFileStreamsWithBuffer(FileDto fileDto, String path) {
        try (
                InputStream in = new ByteArrayInputStream(fileDto.getContent());
                OutputStream out = new BufferedOutputStream(new FileOutputStream(path))) {
            byte[] buffer = new byte[100];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
            System.out.println("The file was successfully copied with File Streams with buffer");
            return true;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }
}
