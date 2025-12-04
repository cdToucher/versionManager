package com.example.vms.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "versions")
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

    // Constructors
    public Version() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = Status.DRAFT;
    }

    public Version(String name, String versionNumber, VersionType type, String description) {
        this();
        this.name = name;
        this.versionNumber = versionNumber;
        this.type = type;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getFoundationVersion() {
        return foundationVersion;
    }

    public void setFoundationVersion(String foundationVersion) {
        this.foundationVersion = foundationVersion;
    }

    public VersionType getType() {
        return type;
    }

    public void setType(VersionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatabaseInitSql() {
        return databaseInitSql;
    }

    public void setDatabaseInitSql(String databaseInitSql) {
        this.databaseInitSql = databaseInitSql;
    }

    public String getDeploymentInfo() {
        return deploymentInfo;
    }

    public void setDeploymentInfo(String deploymentInfo) {
        this.deploymentInfo = deploymentInfo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCustomRequirements() {
        return customRequirements;
    }

    public void setCustomRequirements(String customRequirements) {
        this.customRequirements = customRequirements;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }
}