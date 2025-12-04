package com.anmi.vms.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "version_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionFile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "file_name", nullable = false)
    private String fileName;
    
    @Column(name = "file_path", nullable = false)
    private String filePath;
    
    @Column(name = "file_type")
    private String fileType;
    
    @Column(name = "file_size")
    private Long fileSize;
    
    @Column(name = "description")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id", nullable = false)
    private Version version;
    
    @Column(name = "uploaded_by")
    private Long uploadedBy;
    
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    // Constructor with parameters (excluding auto-generated fields)
    public VersionFile(String fileName, String filePath, String fileType, Long fileSize, String description, Version version, Long uploadedBy) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.description = description;
        this.version = version;
        this.uploadedBy = uploadedBy;
        this.uploadedAt = LocalDateTime.now();
        this.isActive = true;
    }
}