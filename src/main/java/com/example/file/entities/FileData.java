package com.example.file.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class FileData {
    @Id
    @Column(name = "id_file", nullable = false)
    private Long idFile;
    private String fileName;
    private String fileType;

    @Lob
    private byte[] data;


    public FileData(Long id ,String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.idFile = id;
        this.fileType = fileType;
        this.data = data;
    }
}
