package com.anmi.vms.service.impl;

import com.anmi.vms.entity.Version;
import com.anmi.vms.repository.VersionRepository;
import com.anmi.vms.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private VersionRepository versionRepository;

    @Override
    public Version saveVersion(Version version) {
        if (version.getId() == null) {
            version.setCreatedAt(LocalDateTime.now());
        }
        version.setUpdatedAt(LocalDateTime.now());
        return versionRepository.save(version);
    }

    @Override
    public Optional<Version> findVersionById(Long id) {
        return versionRepository.findById(id);
    }

    @Override
    public List<Version> findAllVersions() {
        return versionRepository.findAll();
    }

    @Override
    public Page<Version> findAllVersions(Pageable pageable) {
        return versionRepository.findAll(pageable);
    }

    @Override
    public Version updateVersion(Version version) {
        Version existingVersion = versionRepository.findById(version.getId()).orElse(null);
        if (existingVersion != null) {
            version.setCreatedAt(existingVersion.getCreatedAt()); // Preserve creation date
            version.setCreatedBy(existingVersion.getCreatedBy()); // Preserve creator
        }
        version.setUpdatedAt(LocalDateTime.now());
        return versionRepository.save(version);
    }

    @Override
    public void deleteVersionById(Long id) {
        versionRepository.deleteById(id);
    }

    @Override
    public List<Version> findVersionsByStatus(Version.Status status) {
        return versionRepository.findByStatus(status);
    }

    @Override
    public List<Version> findVersionsByType(Version.VersionType type) {
        return versionRepository.findByType(type);
    }

    @Override
    public List<Version> findVersionsByCreatedBy(Long userId) {
        return versionRepository.findByCreatedBy(userId);
    }

    @Override
    public Version submitForApproval(Long id, Long userId) {
        Version version = versionRepository.findById(id).orElse(null);
        if (version != null && version.getStatus() == Version.Status.DRAFT) {
            version.setStatus(Version.Status.PENDING_APPROVAL);
            version.setUpdatedAt(LocalDateTime.now());
            return versionRepository.save(version);
        }
        return null;
    }

    @Override
    public Version approveVersion(Long id, Long approverId) {
        Version version = versionRepository.findById(id).orElse(null);
        if (version != null && version.getStatus() == Version.Status.PENDING_APPROVAL) {
            version.setStatus(Version.Status.APPROVED);
            version.setApprovedBy(approverId);
            version.setApprovedAt(LocalDateTime.now());
            version.setUpdatedAt(LocalDateTime.now());
            return versionRepository.save(version);
        }
        return null;
    }

    @Override
    public Version rejectVersion(Long id, Long approverId) {
        Version version = versionRepository.findById(id).orElse(null);
        if (version != null && version.getStatus() == Version.Status.PENDING_APPROVAL) {
            version.setStatus(Version.Status.REJECTED);
            version.setApprovedBy(approverId);
            version.setApprovedAt(LocalDateTime.now());
            version.setUpdatedAt(LocalDateTime.now());
            return versionRepository.save(version);
        }
        return null;
    }
}