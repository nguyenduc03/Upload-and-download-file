package com.example.file.services.Interface;

import com.example.file.entities.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    public Object uploadFile(MultipartFile file) throws IOException;
    FileData getFile(Long fileId) throws Exception;
}
