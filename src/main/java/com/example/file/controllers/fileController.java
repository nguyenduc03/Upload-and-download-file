package com.example.file.controllers;

import com.example.file.entities.FileData;
import com.example.file.models.InfoFileDownload;
import com.example.file.services.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@Controller
@RestController
@RequestMapping("/api/file")
public class fileController {
    @Autowired
    private FileServiceImpl fileService;

    @PostMapping("/upload")
    public Object downloadFile(@RequestParam("file") MultipartFile file) throws IOException {

        return ResponseEntity.ok()
                .body(fileService.uploadFile(file));
    }

    @PostMapping("/download")
    public ResponseEntity<Object> download(@RequestBody InfoFileDownload infoFileDownload) throws Exception {
        FileData temp = fileService.getFile(infoFileDownload.getCode());
        return ResponseEntity.ok().contentType(MediaType.valueOf(temp.getFileType())).header("name",temp.getFileName()).body(temp.getData());
    }
}
