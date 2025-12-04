package com.anmi.vms.controller;

import com.anmi.vms.entity.Version;
import com.anmi.vms.service.VersionService;
import com.anmi.vms.util.PdfGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/export")
@Tag(name = "Export", description = "APIs for exporting version information")
public class ExportController {

    @Autowired
    private VersionService versionService;

    @Operation(summary = "Export version as ZIP", description = "Export version information as a ZIP file containing PDF and other resources")
    @GetMapping("/version/{id}/zip")
    public ResponseEntity<ByteArrayResource> exportVersionAsZip(@PathVariable Long id) {
        var versionOpt = versionService.findVersionById(id);
        if (versionOpt.isPresent()) {
            Version version = versionOpt.get();
            try {
                // Create ZIP file
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ZipOutputStream zipOut = new ZipOutputStream(baos);

                // Add version info as PDF
                byte[] pdfBytes = PdfGenerator.generateVersionInfoPdf(version);
                if (pdfBytes != null) {
                    ZipEntry pdfEntry = new ZipEntry(version.getName() + "_info.pdf");
                    zipOut.putNextEntry(pdfEntry);
                    zipOut.write(pdfBytes);
                    zipOut.closeEntry();
                }

                // Add database initialization SQL if available
                if (version.getDatabaseInitSql() != null && !version.getDatabaseInitSql().isEmpty()) {
                    ZipEntry sqlEntry = new ZipEntry(version.getName() + "_init.sql");
                    zipOut.putNextEntry(sqlEntry);
                    zipOut.write(version.getDatabaseInitSql().getBytes());
                    zipOut.closeEntry();
                }

                // Add deployment information if available
                if (version.getDeploymentInfo() != null && !version.getDeploymentInfo().isEmpty()) {
                    ZipEntry deployEntry = new ZipEntry(version.getName() + "_deployment.txt");
                    zipOut.putNextEntry(deployEntry);
                    zipOut.write(version.getDeploymentInfo().getBytes());
                    zipOut.closeEntry();
                }

                // Add custom requirements if available
                if (version.getCustomRequirements() != null && !version.getCustomRequirements().isEmpty()) {
                    ZipEntry customEntry = new ZipEntry(version.getName() + "_custom_requirements.txt");
                    zipOut.putNextEntry(customEntry);
                    zipOut.write(version.getCustomRequirements().getBytes());
                    zipOut.closeEntry();
                }

                zipOut.close();

                // Prepare response
                ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());
                
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + version.getName() + "_export.zip")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Export version as PDF", description = "Export version information as a PDF file")
    @GetMapping("/version/{id}/pdf")
    public ResponseEntity<ByteArrayResource> exportVersionAsPdf(@PathVariable Long id) {
        return versionService.findVersionById(id)
                .map(version -> {
                    byte[] pdfBytes = PdfGenerator.generateVersionInfoPdf(version);
                    if (pdfBytes != null) {
                        ByteArrayResource resource = new ByteArrayResource(pdfBytes);
                        return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + version.getName() + "_info.pdf")
                                .contentType(MediaType.APPLICATION_PDF)
                                .body(resource);
                    } else {
                        return ResponseEntity.internalServerError().build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }
}