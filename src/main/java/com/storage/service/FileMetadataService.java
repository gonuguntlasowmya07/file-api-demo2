package com.storage.service;

import com.storage.entities.FileMetadata;

import java.io.File;
import java.util.List;

public interface FileMetadataService {
    public FileMetadata byFileId(Long id);
    public FileMetadata create(FileMetadata fileMetadata);
    public List<FileMetadata> getAll();
}
