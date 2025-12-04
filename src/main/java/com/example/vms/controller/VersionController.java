package com.example.vms.controller;

import com.example.vms.entity.Version;
import com.example.vms.service.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/versions")
@Tag(name = "版本管理", description = "版本管理相关API")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @Operation(summary = "获取所有版本", description = "分页获取所有版本列表")
    @GetMapping
    public ResponseEntity<Page<Version>> getAllVersions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Version> versions = versionService.findAllVersions(pageable);
        return ResponseEntity.ok(versions);
    }

    @Operation(summary = "根据ID获取版本", description = "根据ID获取版本信息")
    @GetMapping("/{id}")
    public ResponseEntity<Version> getVersionById(@PathVariable Long id) {
        return versionService.findVersionById(id)
                .map(version -> ResponseEntity.ok(version))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "创建新版本", description = "创建新版本记录")
    @PostMapping
    public ResponseEntity<Version> createVersion(@Valid @RequestBody Version version, @RequestParam Long userId) {
        // Set the creator ID
        version.setCreatedBy(userId);
        Version savedVersion = versionService.saveVersion(version);
        return ResponseEntity.ok(savedVersion);
    }

    @Operation(summary = "更新版本", description = "更新现有版本")
    @PutMapping("/{id}")
    public ResponseEntity<Version> updateVersion(@PathVariable Long id, @Valid @RequestBody Version version) {
        version.setId(id);
        Version updatedVersion = versionService.updateVersion(version);
        return ResponseEntity.ok(updatedVersion);
    }

    @Operation(summary = "删除版本", description = "根据ID删除版本")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVersion(@PathVariable Long id) {
        versionService.deleteVersionById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "提交版本审批", description = "提交版本进行审批")
    @PostMapping("/{id}/submit-approval")
    public ResponseEntity<Version> submitForApproval(@PathVariable Long id, @RequestParam Long userId) {
        Version version = versionService.submitForApproval(id, userId);
        if (version != null) {
            return ResponseEntity.ok(version);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "审批版本", description = "审批版本")
    @PostMapping("/{id}/approve")
    public ResponseEntity<Version> approveVersion(@PathVariable Long id, @RequestParam Long approverId) {
        Version version = versionService.approveVersion(id, approverId);
        if (version != null) {
            return ResponseEntity.ok(version);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "拒绝版本", description = "拒绝版本")
    @PostMapping("/{id}/reject")
    public ResponseEntity<Version> rejectVersion(@PathVariable Long id, @RequestParam Long approverId) {
        Version version = versionService.rejectVersion(id, approverId);
        if (version != null) {
            return ResponseEntity.ok(version);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "根据状态获取版本", description = "根据状态获取版本")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Version>> getVersionsByStatus(@PathVariable Version.Status status) {
        List<Version> versions = versionService.findVersionsByStatus(status);
        return ResponseEntity.ok(versions);
    }

    @Operation(summary = "根据类型获取版本", description = "根据类型获取版本")
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Version>> getVersionsByType(@PathVariable Version.VersionType type) {
        List<Version> versions = versionService.findVersionsByType(type);
        return ResponseEntity.ok(versions);
    }

    @Operation(summary = "根据创建者获取版本", description = "获取特定用户创建的版本")
    @GetMapping("/created-by/{userId}")
    public ResponseEntity<List<Version>> getVersionsByCreator(@PathVariable Long userId) {
        List<Version> versions = versionService.findVersionsByCreatedBy(userId);
        return ResponseEntity.ok(versions);
    }
}