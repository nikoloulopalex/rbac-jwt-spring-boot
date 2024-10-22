package com.roles.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.roles.model.FileInfo;
import com.roles.payload.response.FileResponseMessage;
import com.roles.service.FilesStorageService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FilesController {

    private FilesStorageService storageService;

    public FilesController(FilesStorageService storageService) {
        this.storageService = storageService;
    }
    
    @GetMapping("api/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
      List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
        String filename = path.getFileName().toString();
        String url = storageService.getPath(filename);
        return new FileInfo(filename, url);
      }).collect(Collectors.toList());

      return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("api/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    

    @PostMapping("api/upload-file")
    @ResponseBody
    public FileResponseMessage uploadFile(@RequestParam("file") MultipartFile file) {
        String name = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();

        return new FileResponseMessage(name, uri, file.getContentType(), file.getSize());
    }

    @PostMapping("api/upload-multiple-files")
    @ResponseBody
    public List<FileResponseMessage> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
}