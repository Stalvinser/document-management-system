package com.astarus.documentmanagementsystem.document.file;

import lombok.AllArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public ResponseEntity<?> viewFile(String fileUuid) throws IOException {
        Optional<FileEntity> fileOptional = fileRepository.findByUuid(fileUuid);
        if (fileOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        FileEntity fileEntity = fileOptional.get();
        String filePath = "files" + File.separator + fileEntity.getDocument().getAppUser().getEmail() + File.separator + fileUuid;
        Path path = Paths.get(filePath);
        Resource resource = new UrlResource(path.toUri());

        String contentType = new Tika().detect(path.toFile());

        if ("application/pdf".equals(contentType)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileEntity.getFileName() + "\"")
                    .body(resource);
        } else if ("application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)) {
            String htmlContent = getDocumentAsHtml(filePath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);
            headers.add(HttpHeaders.CONTENT_TYPE, "text/html; charset=UTF-8");
            return new ResponseEntity<>(htmlContent, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                    .body(resource);
        }
    }
    private String getDocumentAsHtml(String filePath) {
        try (InputStream input = new FileInputStream(filePath)) {
            ContentHandler handler = new ToHTMLContentHandler();
            Metadata metadata = new Metadata();
            ParseContext context = new ParseContext();

            // Using OOXMLParser to specifically handle .docx format
            Parser parser = new OOXMLParser();
            parser.parse(input, handler, metadata, context);
            return handler.toString();
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
            return "Error converting DOCX to HTML: " + e.getMessage();
        }
    }
}
