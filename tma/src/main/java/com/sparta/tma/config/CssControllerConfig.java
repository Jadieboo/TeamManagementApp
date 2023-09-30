package com.sparta.tma.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CssControllerConfig {

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
}
