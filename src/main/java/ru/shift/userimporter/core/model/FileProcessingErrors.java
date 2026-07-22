package ru.shift.userimporter.core.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "file_processing_errors")
public class FileProcessingErrors {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private UploadedFiles uploadedFile;

    @Column(name = "row_number", nullable = false)
    private Integer rowNumber;

    @Column(name = "error_message", nullable = false)
    private String errorMessage;

    @Column(name = "raw_data")
    private String rawData;
}
