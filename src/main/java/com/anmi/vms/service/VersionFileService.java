package com.anmi.vms.service;

import com.anmi.vms.entity.VersionFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VersionFileService {
    VersionFile saveVersionFile(VersionFile versionFile);
    VersionFile uploadFileToVersion(MultipartFile file, Long versionId, String description, Long uploadedBy);
    List<VersionFile> findFilesByVersionId(Long versionId);
    VersionFile findFileById(Long id);
    void deleteFileById(Long id);
    String getFilePath(Long fileId);
}