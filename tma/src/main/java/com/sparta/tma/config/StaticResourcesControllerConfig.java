package com.sparta.tma.config;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StaticResourcesControllerConfig {

    @GetMapping(value = "/css/{fileName:.+}", produces = "text/css")
    public ResponseEntity<Resource> getCssFile(@PathVariable String fileName) {
        String cssFilePath = "static/css/" + fileName;
        // Load the CSS file as a resource from the classpath
        Resource resource = new ClassPathResource(cssFilePath);

        // Return a ResponseEntity with the CSS file and appropriate headers
        return ResponseEntity.ok()
                .header("Content-Disposition", "inline")
                .body(resource);
    }

    @GetMapping(value = "/images/{imageName:.+}")
    public ResponseEntity<Resource> getImageFile(@PathVariable String imageName) {
        String imageFilePath = "static/images/" + imageName;
        // Load the image file as a resource from the classpath
        Resource resource = new ClassPathResource(imageFilePath);

        // Determine the appropriate MIME type based on the file extension
        String contentType = determineContentType(imageName);

        // Return a ResponseEntity with the image file and appropriate headers
        return ResponseEntity.ok()
                .header("Content-Disposition", "inline")
                .contentType(MediaType.valueOf(contentType))
                .body(resource);
    }

    private String determineContentType(String imageName) {
        if (imageName.endsWith(".png")) {
            return "image/png";
        } else if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (imageName.endsWith(".gif")) {
            return "image/gif";
        } else if (imageName.endsWith(".bmp")) {
            return "image/bmp";
        } else if (imageName.endsWith(".webp")) {
            return "image/webp";
        } else if (imageName.endsWith(".svg")) {
            return "image/svg+xml";
        } else {
            // If the file extension is not recognized, set a default content type
            return "application/octet-stream";
        }
    }
}
