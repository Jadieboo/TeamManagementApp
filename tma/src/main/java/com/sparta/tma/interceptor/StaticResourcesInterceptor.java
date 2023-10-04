package com.sparta.tma.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.OutputStream;

public class StaticResourcesInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("in pre handle method");

        logger.info("request: {}", request);

        String requestPath = request.getServletPath();

        logger.info("request path:" + requestPath);

        if (requestPath.startsWith("/css/")) {
            serveResource(response, "static" + requestPath, "text/css");
            return false;
        } else if (requestPath.startsWith("/images/")) {
            String contentType = getContentTypeFromExtension(requestPath);
            serveResource(response,"static" + requestPath, contentType);
            return false;
        }
        return true;
    }

    private void serveResource(HttpServletResponse response, String resourcePath, String contentType) {
        try {
            Resource resource = new ClassPathResource(resourcePath);
            response.setContentType(contentType);
            response.setContentLengthLong(resource.contentLength());

            try (OutputStream out = response.getOutputStream()) {
                byte[] data = resource.getInputStream().readAllBytes();
                out.write(data);
                out.flush();
            }
        } catch (IOException e) {
            // Handle exceptions if needed
            e.printStackTrace();
        }
    }

    private String getContentTypeFromExtension(String filePath) {
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1);
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG_VALUE;
            case "png":
                return MediaType.IMAGE_PNG_VALUE;
            case "gif":
                return MediaType.IMAGE_GIF_VALUE;
            case "bmp":
                return "image/bmp";
            case "webp":
                return "image/webp";
            case "svg":
                return "image/svg+xml";
            default:
                return "application/octet-stream"; // If the file extension is not recognized, set a default content type
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // Do nothing here
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Do nothing here
    }


}
