package com.anmi.vms.controller;

import com.anmi.vms.entity.Version;
import com.anmi.vms.service.VersionService;
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
@Tag(name = "Version Management", description = "APIs for version management")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @Operation(summary = "获取所有版本", description = "分页获取所有版本信息")
    @GetMapping
    public ResponseEntity<Page<Version>> getAllVersions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Version> versions = versionService.findAllVersions(pageable);
        return ResponseEntity.ok(versions);
    }

    @Operation(summary = "获取所有版本列表", description = "获取所有版本的列表（不分页）")
    @GetMapping("/list")
    public ResponseEntity<List<Version>> getAllVersionsList() {
        List<Version> versions = versionService.findAllVersions();
        return ResponseEntity.ok(versions);
    }

    @Operation(summary = "Get version by ID", description = "Retrieve a version by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<Version> getVersionById(@PathVariable Long id) {
        return versionService.findVersionById(id)
                .map(version -> ResponseEntity.ok(version))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new version", description = "Create a new version record")
    @PostMapping
    public ResponseEntity<Version> createVersion(@Valid @RequestBody Version version, @RequestParam Long userId) {
        // Set the creator ID
        version.setCreatedBy(userId);
        Version savedVersion = versionService.saveVersion(version);
        return ResponseEntity.ok(savedVersion);
    }

    @Operation(summary = "Update version", description = "Update an existing version")
    @PutMapping("/{id}")
    public ResponseEntity<Version> updateVersion(@PathVariable Long id, @Valid @RequestBody Version version) {
        version.setId(id);
        Version updatedVersion = versionService.updateVersion(version);
        return ResponseEntity.ok(updatedVersion);
    }

    @Operation(summary = "Delete version", description = "Delete a version by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVersion(@PathVariable Long id) {
        versionService.deleteVersionById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Submit version for approval", description = "Submit a version for approval")
    @PostMapping("/{id}/submit-approval")
    public ResponseEntity<Version> submitForApproval(@PathVariable Long id, @RequestParam Long userId) {
        Version version = versionService.submitForApproval(id, userId);
        if (version != null) {
            return ResponseEntity.ok(version);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Approve version", description = "Approve a version")
    @PostMapping("/{id}/approve")
    public ResponseEntity<Version> approveVersion(@PathVariable Long id, @RequestParam Long approverId) {
        Version version = versionService.approveVersion(id, approverId);
        if (version != null) {
            return ResponseEntity.ok(version);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Reject version", description = "Reject a version")
    @PostMapping("/{id}/reject")
    public ResponseEntity<Version> rejectVersion(@PathVariable Long id, @RequestParam Long approverId) {
        Version version = versionService.rejectVersion(id, approverId);
        if (version != null) {
            return ResponseEntity.ok(version);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get versions by status", description = "Retrieve versions by their status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Version>> getVersionsByStatus(@PathVariable Version.Status status) {
        List<Version> versions = versionService.findVersionsByStatus(status);
        return ResponseEntity.ok(versions);
    }

    @Operation(summary = "Get versions by type", description = "Retrieve versions by their type")
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Version>> getVersionsByType(@PathVariable Version.VersionType type) {
        List<Version> versions = versionService.findVersionsByType(type);
        return ResponseEntity.ok(versions);
    }

    @Operation(summary = "Get versions by creator", description = "Retrieve versions created by a specific user")
    @GetMapping("/created-by/{userId}")
    public ResponseEntity<List<Version>> getVersionsByCreator(@PathVariable Long userId) {
        List<Version> versions = versionService.findVersionsByCreatedBy(userId);
        return ResponseEntity.ok(versions);
    }
}