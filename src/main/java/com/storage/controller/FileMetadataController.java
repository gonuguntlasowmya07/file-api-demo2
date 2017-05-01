package com.storage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.entities.FileMetadata;
import com.storage.service.FileMetadataService;
import com.storage.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;

@RestController
public class FileMetadataController {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private FileMetadataService fileMetadataService;

    @GetMapping("/files/{id:[0-9]+}")
    public FileMetadata getMetadata(@PathVariable Long id) {
        final FileMetadata fileMetadata = fileMetadataService.byFileId(id);
        if(fileMetadata == null) {
            throw new StorageFileNotFoundException("file not found");
        }
        return fileMetadata;
    }

    @GetMapping("/files")
    public String getAll() throws Exception {
        final StringWriter sw =new StringWriter();
        objectMapper.writeValue(sw, fileMetadataService.getAll());
        final String jsonData = sw.toString();
        sw.close();
        return jsonData;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}
