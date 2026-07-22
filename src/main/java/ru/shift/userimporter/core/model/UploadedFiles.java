package ru.shift.userimporter.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "uploaded_files")
public class UploadedFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "uploadedFile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileProcessingErrors> errors = new ArrayList<>();

    @Column(name = "total_rows")
    private Integer totalRows;

    @Column(name = "processed_rows")
    private Integer processedRows;

    @Column(name = "valid_rows")
    private Integer validRows;

    @Column(name = "invalid_rows")
    private Integer invalidRows;

    @Column(name = "original_filename", length = 50)
    private String originalFilename;

    @Column(name = "storage_path", length = 512)
    private String storagePath;

    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private StatusError status;

    @Column(name = "hash", length = 40)
    private String hash;
}
