package com.storage.repository;

import com.storage.entities.FileMetadata;
import org.springframework.data.repository.CrudRepository;

public interface FileMetaDataRespository extends CrudRepository<FileMetadata, Long> {
}
