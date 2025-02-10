package com.gabo.kchika.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.gabo.kchika.dtos.PageRequest;
import com.gabo.kchika.dtos.PageResponse;
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

}
