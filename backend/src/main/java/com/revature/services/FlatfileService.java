package com.revature.services;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlatfileService {

    public String uploadFile(MultipartFile file) {

        if (file.isEmpty()) {
            return "Please select a file to upload.";
        }
        try {
            // Get the file name
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            File directory = new File("uploads");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Save the file to the uploads directory
            Path filePath = Paths.get("uploads", fileName);
            if (Files.exists(filePath)) {
                return "File with the same name already exists: " + fileName;
            }
            Files.write(filePath, file.getBytes());

            return "File uploaded successfully: " + fileName;
        } catch (Exception e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }

    public List<String> getAllFiles() {
        List<String> files = new ArrayList<>();

        String folderPath = "uploads";

        // Get all files in the folder
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    files.add(file.getName());
                }
            }
        }

        return files;
    }

    public ResponseEntity<InputStreamResource> downloadFile(String filename) throws IOException {
        // Assuming the files are stored in a directory named "uploads" within the project directory
        String filePath = "/Users/user/Desktop/lorenzo-file-parser/backend/uploads/" + filename;
        File file = new File(filePath);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Set content type and headers
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentDispositionFormData("attachment", filename);

        // Create InputStreamResource from file
        InputStream inputStream = new FileInputStream(file);
        InputStreamResource resource = new InputStreamResource(inputStream);

        // Return ResponseEntity with InputStreamResource and headers
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
