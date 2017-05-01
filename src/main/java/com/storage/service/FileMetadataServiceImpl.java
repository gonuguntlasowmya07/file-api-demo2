package com.storage.service;

import com.storage.entities.FileMetadata;
import com.storage.repository.FileMetaDataRespository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Component("fileMetadataService")
@Transactional
public class FileMetadataServiceImpl implements FileMetadataService {

    private final FileMetaDataRespository fileMetaDataRespository;

    public FileMetadataServiceImpl(FileMetaDataRespository fileMetaDataRespository) {
        this.fileMetaDataRespository = fileMetaDataRespository;
    }

    @Override
    public FileMetadata byFileId(Long id) {
        return fileMetaDataRespository.findOne(id);
    }

    @Override
    public FileMetadata create(FileMetadata fileMetadata) {
        return fileMetaDataRespository.save(fileMetadata);
    }

    public List<FileMetadata> getAll() {
        final Iterable<FileMetadata> all = fileMetaDataRespository.findAll();
        List<FileMetadata> target = new ArrayList<>();
        all.forEach(fm -> target.add(fm));
        return target;
    }
}
