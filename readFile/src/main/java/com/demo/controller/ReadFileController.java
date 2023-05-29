package com.demo.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ReadFileController {
    final ResourceLoader resourceLoader;

    @GetMapping("/readFile")
    public ResponseEntity<Resource> readFile() throws Exception {
        var resource = resourceLoader.getResource("classpath:application.yml");
        var inputStreamResource = new InputStreamResource(resource.getInputStream());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tutorials.yml")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                //.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(inputStreamResource);
    }

    @GetMapping("/getAllFileName")
    public List getAllFileName() throws Exception {
        var url = "classpath:static/*";
        Resource[] resources = ((ResourcePatternResolver) resourceLoader).getResources(url);
        return Arrays.stream(resources)
                .map(Resource::getFilename)
                .collect(Collectors.toList());
    }

    @GetMapping("/api-1")
    public String api1() throws Exception {
        var result = "api-1";
        TimeUnit.SECONDS.sleep(3);
        System.out.printf("AAA 1: " + result);
        return result;
    }

    @GetMapping("/api-2")
    public String api2() throws Exception {
        var result = "api-2";
//        TimeUnit.SECONDS.sleep(3);
        System.out.printf("AAA 2: " + result);
        return result;
    }
}
