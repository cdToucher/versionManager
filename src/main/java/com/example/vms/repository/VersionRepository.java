package com.example.vms.repository;

import com.example.vms.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersionRepository extends JpaRepository<Version, Long> {
    List<Version> findByStatus(Version.Status status);
    List<Version> findByType(Version.VersionType type);
    List<Version> findByCreatedBy(Long userId);
    List<Version> findByStatusAndType(Version.Status status, Version.VersionType type);
}