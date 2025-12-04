package com.anmi.vms.controller;

import com.anmi.vms.entity.VersionFile;
import com.anmi.vms.service.VersionFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/version-files")
@Tag(name = "版本文件管理", description = "版本文件上传和管理API")
public class VersionFileController {

    @Autowired
    private VersionFileService versionFileService;

    @Operation(summary = "上传文件到版本", description = "为指定版本上传文件")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VersionFile> uploadFileToVersion(
            @RequestParam("file") MultipartFile file,
            @RequestParam("versionId") Long versionId,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "uploadedBy", required = false) Long uploadedBy) {
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        VersionFile uploadedFile = versionFileService.uploadFileToVersion(file, versionId, description, uploadedBy);
        return ResponseEntity.ok(uploadedFile);
    }

    @Operation(summary = "获取版本的所有文件", description = "获取指定版本上传的所有文件")
    @GetMapping("/version/{versionId}")
    public ResponseEntity<List<VersionFile>> getFilesByVersionId(@PathVariable Long versionId) {
        List<VersionFile> files = versionFileService.findFilesByVersionId(versionId);
        return ResponseEntity.ok(files);
    }

    @Operation(summary = "下载文件", description = "下载指定的版本文件")
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        String filePath = versionFileService.getFilePath(fileId);
        if (filePath == null) {
            return ResponseEntity.notFound().build();
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

    @Operation(summary = "删除文件", description = "删除指定的版本文件")
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        versionFileService.deleteFileById(fileId);
        return ResponseEntity.noContent().build();
    }
}