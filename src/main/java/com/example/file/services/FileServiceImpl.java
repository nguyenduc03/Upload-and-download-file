package com.example.file.services;

import com.example.file.entities.FileData;
import com.example.file.models.FileUploadResult;
import com.example.file.repository.FileRepository;
import com.example.file.services.Interface.FileService;
import com.example.file.utils.Contants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    FileRepository fileRepository;
    private boolean checkRequire(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null)
            return false;
        String filename = file.getOriginalFilename();
        String require = "pdf docx xls pptx jpg";
        String[] nameSplit = filename.split("\\.");
        return (require.contains(nameSplit[nameSplit.length - 1]));
    }

    public Object uploadFile(MultipartFile file) throws IOException {
        FileUploadResult fileUploadResult = new FileUploadResult();
        if (checkRequire(file)) {
            byte[] bytes = file.getBytes();
            File dir = new File(Contants.path);
            if (!dir.exists())
                dir.mkdirs();
            Long Code = Calendar.getInstance().getTimeInMillis();
            File serverFile = new File(dir.getAbsolutePath()
                    + File.separator + Code + file.getOriginalFilename());
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            fileUploadResult.setFileName(file.getOriginalFilename());
            stream.close();
            fileUploadResult.setMessage(Contants.uploadSuccess);
            fileUploadResult.setCode(Code.toString());

            FileData fileData
                    = new FileData(Code,Code + file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes());
            fileRepository.save(fileData);
        } else
            fileUploadResult.setMessage(Contants.wrongFileFormat);
        return fileUploadResult;
    }

    @Override
    public FileData getFile(Long fileId) throws Exception {
        return fileRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }
}
