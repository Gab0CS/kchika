package com.gabo.kchika.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.GetExchange;

import com.gabo.kchika.dtos.PageRequest;
import com.gabo.kchika.dtos.PageResponse;
import com.gabo.kchika.dtos.PostRequest;
import com.gabo.kchika.services.PageService;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

// @Controller se utiliza más para patrón MVC y directamente con servlets 
@RestController //Use to expose RESTFull
@RequestMapping(path ="page") //Access to this controller
@AllArgsConstructor
public class PageController {
    
    private final PageService pageService;

    //Allows to send an status, manipulate body and headers, use to get data
    @GetMapping(path = "{title}")
    public ResponseEntity<PageResponse> getPage(@PathVariable String title){
        return ResponseEntity.ok(this.pageService.readByTitle(title));
    }

    // use to create data
    @PostMapping
    public ResponseEntity<?> postPage(@RequestBody PageRequest request) {
        request.setTitle(this.normalizeTitle(request.getTitle()));
        final var uri = this.pageService.create(request).getTitle();
        return ResponseEntity.created(URI.create(uri)).build();
    }


    @PutMapping(path = "{title}")
    public ResponseEntity<PageResponse> updatePage(
        @PathVariable String title, @RequestBody PageRequest request
    ){
        request.setTitle(this.normalizeTitle(request.getTitle()));
        return ResponseEntity.ok(this.pageService.update(request, title));
    }

    @DeleteMapping(path = "{title}")
    public ResponseEntity<Void> deletePage(@PathVariable String title){
        this.pageService.delete(title);

        return ResponseEntity.noContent().build();
    }

    private String normalizeTitle(String title) {
        if (title.contains(" ")) {
            return title.replaceAll(" ", "-");
        } else {
            return title;
        }
    }

    @PostMapping(path = "{title}/post")
    public ResponseEntity<PageResponse> postPage(
        @RequestBody PostRequest request,
        @PathVariable String title) {
        
        return ResponseEntity.ok(this.pageService.createPost(request, title));
    }

    @DeleteMapping(path = "{title}/post/{idPost}")
    public ResponseEntity<Void> deletePage(
        @PathVariable String title,
        @PathVariable Long idPost) {
        this.pageService.deletePost(idPost, title); 
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "img/upload")
    public ResponseEntity<String> upload(
         @RequestParam(value = "file" ) MultipartFile file){
            try {
                final var pathUrl = "/Volumes/UGREEN/Developer/web/kchika/src/main/resources/static/img";
                final var path = Paths.get(pathUrl);
                if(!Files.exists(path)){
                    Files.createDirectory(path);
                }
                final var fullName = pathUrl + "/" + file.getOriginalFilename();
                final var destination = new File(pathUrl);
                file.transferTo(destination);

                return ResponseEntity.ok("Upload success on: " + fullName);

            } catch (IOException e) {
                return ResponseEntity.internalServerError().body("Can't upload img");
            }
    }


}
