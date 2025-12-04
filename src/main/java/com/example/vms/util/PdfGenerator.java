package com.example.vms.util;

import com.example.vms.entity.Version;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.property.UnitValue;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

public class PdfGenerator {

    public static byte[] generateVersionInfoPdf(Version version) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            // Add title
            document.add(new Paragraph("Version Information Report")
                    .setFontSize(20)
                    .setBold());
            
            document.add(new Paragraph(" "));
            
            // Add version details
            document.add(new Paragraph("Version Name: " + version.getName()));
            document.add(new Paragraph("Version Number: " + version.getVersionNumber()));
            document.add(new Paragraph("Type: " + version.getType()));
            document.add(new Paragraph("Status: " + version.getStatus()));
            document.add(new Paragraph("Foundation Version: " + 
                    (version.getFoundationVersion() != null ? version.getFoundationVersion() : "N/A")));
            document.add(new Paragraph("Created At: " + 
                    (version.getCreatedAt() != null ? 
                            version.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "N/A")));
            document.add(new Paragraph("Description: " + 
                    (version.getDescription() != null ? version.getDescription() : "N/A")));
            
            document.add(new Paragraph(" "));
            
            // Add deployment information
            if (version.getDeploymentInfo() != null && !version.getDeploymentInfo().isEmpty()) {
                document.add(new Paragraph("Deployment Information:")
                        .setFontSize(14)
                        .setBold());
                document.add(new Paragraph(version.getDeploymentInfo()));
                document.add(new Paragraph(" "));
            }
            
            // Add database initialization SQL
            if (version.getDatabaseInitSql() != null && !version.getDatabaseInitSql().isEmpty()) {
                document.add(new Paragraph("Database Initialization SQL:")
                        .setFontSize(14)
                        .setBold());
                document.add(new Paragraph(version.getDatabaseInitSql()));
                document.add(new Paragraph(" "));
            }
            
            // Add custom requirements
            if (version.getCustomRequirements() != null && !version.getCustomRequirements().isEmpty()) {
                document.add(new Paragraph("Custom Requirements:")
                        .setFontSize(14)
                        .setBold());
                document.add(new Paragraph(version.getCustomRequirements()));
            }
            
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        return outputStream.toByteArray();
    }
}