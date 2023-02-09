package com.epam.datastorage.controller;

import com.epam.datastorage.dto.FileDto;
import com.epam.datastorage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@RestController
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping(value = "/upload")
    public @ResponseBody
    String handleFileUpload(@RequestParam("name") String name,
                            @RequestPart("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                //           byte[] bytes = file.getBytes();
                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                final InputStream in = file.getInputStream();
                final byte[] buffer = new byte[500];
                int read = -1;
                while ((read = in.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, read);
                }
                in.close();
                byte[] content = outputStream.toByteArray();
                FileDto fileDto = new FileDto();
                fileDto.setFileName(name);
                fileDto.setContent(content);
                fileService.upload(fileDto);
                return "You have successfully downloaded " + name + " в " + name + "-uploaded !";
            } catch (Exception e) {
                return "You failed to download " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to download " + name + " because the file is empty.";
        }
    }

    @GetMapping(value = "/download/{id}")
    public @ResponseBody HttpStatus handleFileDownload(@PathVariable Integer id,
                                                    @RequestParam("path") String path) {
        boolean isDownload = fileService.download(id,path);
        return isDownload ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @GetMapping(value = "/")
    public @ResponseBody
    String status() {
        return "Hello, World!";
    }
}

