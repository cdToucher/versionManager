package com.anmi.vms.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "versions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Version {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "version_number", nullable = false)
    private String versionNumber;
    
    @Column(name = "foundation_version")
    private String foundationVersion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VersionType type;
    
    @Column(length = 1000)
    private String description;
    
    @Column(name = "database_init_sql", length = 10000)
    private String databaseInitSql;
    
    @Column(name = "deployment_info", length = 5000)
    private String deploymentInfo;
    
    @Column(name = "branch_name")
    private String branchName;
    
    @Column(name = "custom_requirements", length = 10000)
    private String customRequirements;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "approved_by")
    private Long approvedBy;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
    
    public enum VersionType {
        FOUNDATION, CUSTOM
    }
    
    public enum Status {
        DRAFT, PENDING_APPROVAL, APPROVED, REJECTED
    }

    // Constructor with parameters (excluding auto-generated fields)
    public Version(String name, String versionNumber, VersionType type, String description) {
        this.name = name;
        this.versionNumber = versionNumber;
        this.type = type;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = Status.DRAFT;
    }
}