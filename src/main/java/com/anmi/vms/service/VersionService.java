package com.anmi.vms.service;

import com.anmi.vms.entity.Version;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface VersionService {
    Version saveVersion(Version version);
    Optional<Version> findVersionById(Long id);
    List<Version> findAllVersions();
    Page<Version> findAllVersions(Pageable pageable);
    Version updateVersion(Version version);
    void deleteVersionById(Long id);
    List<Version> findVersionsByStatus(Version.Status status);
    List<Version> findVersionsByType(Version.VersionType type);
    List<Version> findVersionsByCreatedBy(Long userId);
    Version submitForApproval(Long id, Long userId);
    Version approveVersion(Long id, Long approverId);
    Version rejectVersion(Long id, Long approverId);
}