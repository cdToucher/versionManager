package com.anmi.vms.service.impl;

import com.anmi.vms.entity.Version;
import com.anmi.vms.entity.VersionFile;
import com.anmi.vms.repository.VersionFileRepository;
import com.anmi.vms.service.VersionFileService;
import com.anmi.vms.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class VersionFileServiceImpl implements VersionFileService {

    @Autowired
    private VersionFileRepository versionFileRepository;
    
    @Autowired
    private VersionService versionService;
    
    @Value("${file.upload.path:uploads/}")
    private String uploadPath;

    @Override
    public VersionFile saveVersionFile(VersionFile versionFile) {
        return versionFileRepository.save(versionFile);
    }

    @Override
    public VersionFile uploadFileToVersion(MultipartFile file, Long versionId, String description, Long uploadedBy) {
        try {
            // 检查版本是否存在
            Optional<Version> versionOpt = versionService.findVersionById(versionId);
            if (!versionOpt.isPresent()) {
                throw new RuntimeException("版本不存在: " + versionId);
            }
            
            Version version = versionOpt.get();
            
            // 创建上传目录
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            
            // 生成唯一文件名
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = System.currentTimeMillis() + "_" + originalFileName;
            String filePath = uploadPath + fileName;
            
            // 保存文件到服务器
            Path targetPath = Paths.get(filePath);
            Files.copy(file.getInputStream(), targetPath);
            
            // 创建VersionFile实体
            VersionFile versionFile = new VersionFile(
                originalFileName,
                filePath,
                file.getContentType(),
                file.getSize(),
                description,
                version,
                uploadedBy
            );
            
            return versionFileRepository.save(versionFile);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败: " + e.getMessage(), e);
        }
    }

    @Override
    public List<VersionFile> findFilesByVersionId(Long versionId) {
        return versionFileRepository.findByVersionIdAndIsActiveTrueOrderByUploadedAtDesc(versionId);
    }

    @Override
    public VersionFile findFileById(Long id) {
        Optional<VersionFile> fileOpt = versionFileRepository.findById(id);
        return fileOpt.orElse(null);
    }

    @Override
    public void deleteFileById(Long id) {
        Optional<VersionFile> fileOpt = versionFileRepository.findById(id);
        if (fileOpt.isPresent()) {
            VersionFile file = fileOpt.get();
            // 删除服务器上的物理文件
            try {
                File physicalFile = new File(file.getFilePath());
                if (physicalFile.exists()) {
                    physicalFile.delete();
                }
            } catch (Exception e) {
                // 记录日志，但继续删除数据库记录
                System.err.println("删除物理文件失败: " + e.getMessage());
            }
            
            // 逻辑删除数据库记录
            file.setIsActive(false);
            versionFileRepository.save(file);
        }
    }

    @Override
    public String getFilePath(Long fileId) {
        VersionFile file = findFileById(fileId);
        return file != null ? file.getFilePath() : null;
    }
}