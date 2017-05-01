package com.storage.controller;

import com.storage.entities.FileMetadata;
import com.storage.service.FileMetadataService;
import com.storage.storage.StorageFileNotFoundException;
import com.storage.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Date;

@Controller
public class FileUploadController {

    private final StorageService storageService;

    private final FileMetadataService fileMetadataService;

    @Autowired
    public FileUploadController(StorageService storageService, FileMetadataService fileMetadataService) {
        this.storageService = storageService;
        this.fileMetadataService = fileMetadataService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        /*model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class, "getFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));*/

        return "uploadForm";
    }

    @GetMapping("/files/{id:[0-9]+}/data")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable Long id) {
        final FileMetadata fileMetadata = fileMetadataService.byFileId(id);
        if(fileMetadata == null) {
            throw new StorageFileNotFoundException("file not found");
        }
        Resource file = storageService.loadAsResource(fileMetadata.getId() + "-" + fileMetadata.getName());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping("/")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setContentType(file.getContentType());
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setSize(file.getSize());
        fileMetadata.setCreatedTime(new Date().toString());
        fileMetadataService.create(fileMetadata);

        final String newName = fileMetadata.getId() + "-" + file.getOriginalFilename();
        System.out.println(" msg=reconstructed-file-name newName=" + newName);
        storageService.store(file, newName);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/files/" + fileMetadata.getId();
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


}
