package com.vornicu.notification_service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemplateService {

    public String loadTemplate(String filename) {
        try {
            ClassPathResource resource = new ClassPathResource("templates/" + filename);

            if (!resource.exists()) {
                throw new RuntimeException("Template-ul NU exista in JAR: " + filename);
            }

            try (InputStream input = resource.getInputStream()) {
                return new String(input.readAllBytes(), StandardCharsets.UTF_8);
            }

        } catch (IOException e) {
            throw new RuntimeException("Nu pot încărca template-ul HTML: " + filename, e);
        }
    }


}
