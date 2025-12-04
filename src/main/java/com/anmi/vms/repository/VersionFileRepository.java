package com.anmi.vms.repository;

import com.anmi.vms.entity.VersionFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersionFileRepository extends JpaRepository<VersionFile, Long> {
    List<VersionFile> findByVersionIdAndIsActiveTrue(Long versionId);
    List<VersionFile> findByVersionIdAndIsActiveTrueOrderByUploadedAtDesc(Long versionId);
}