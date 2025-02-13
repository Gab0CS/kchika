package com.gabo.kchika.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabo.kchika.dtos.PageRequest;
import com.gabo.kchika.dtos.PageResponse;
import com.gabo.kchika.dtos.PostRequest;
import com.gabo.kchika.dtos.PostResponse;
import com.gabo.kchika.entities.PageEntity;
import com.gabo.kchika.entities.PostEntity;
import com.gabo.kchika.exceptions.TitleNotValidException;
import com.gabo.kchika.repositories.PageRepository;
import com.gabo.kchika.repositories.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;
    private final UserRepository userRepository;

    @Override
    public PageResponse create(PageRequest page) {
        this.validTitle(page.getTitle());
        
        //Upsert if id exists update if not creates
        final var entity = new PageEntity();
        
        BeanUtils.copyProperties(page, entity);
        entity.setDateCreation(LocalDateTime.now());
        final var user = this.userRepository.findById(page.getUserId())
            .orElseThrow();
        entity.setUser(user);
        entity.setPosts(new ArrayList<>());

        var pageCreated = this.pageRepository.save(entity);
        
        final var response = new PageResponse();
        BeanUtils.copyProperties(pageCreated, response);
        return response;   

    }

    @Override
    public PageResponse readByTitle(String title) {
        final var entityResponse = this.pageRepository.findByTitle(title)
        .orElseThrow(() -> new IllegalArgumentException("Title not found")); //Find by title and handling errros

        final var response = new PageResponse(); //Create response object
        BeanUtils.copyProperties(entityResponse, response); //Copy properties from entity

        //Get post responses from entity
        final List<PostResponse> postResponses = entityResponse.getPosts()
        .stream() //Convert to stream
        .map(postE ->
            PostResponse //Transforma postEntity into postResponse
                .builder()
                .img(postE.getImg())
                .content(postE.getContent())
                .dateCreation(postE.getDateCreation())
                .build()
        ).toList(); //Convert to list

        response.setPosts(postResponses); //Set list of post
        return response;
    }

    @Override
    public PageResponse update(PageRequest page, String title) {
        this.validTitle(page.getTitle());

        final var entityFromDB = this.pageRepository.findByTitle(title)
        .orElseThrow(() -> new IllegalArgumentException("Title not found")); //Find by title and handling errros

        entityFromDB.setTitle(page.getTitle()); //Update fields from param page

        var pageCreated = this.pageRepository.save(entityFromDB);
        final var response = new PageResponse();
        BeanUtils.copyProperties(pageCreated, response);
        
        return response;
    }

    @Override
    public void delete(String title) {

        // final var entityFromDB = this.pageRepository.findByTitle(title)
        // .orElseThrow(() -> new IllegalArgumentException("Title not found")); //Find by title and handling errros

        // this.pageRepository.delete(entityFromDB);

        if (this.pageRepository.existsByTitle(title)) {
            log.info("Delinting page");
            this.pageRepository.deleteByTitle(title);
        } else {
            log.error("Error to delete");
            throw new IllegalArgumentException("Cant delete because id not exist");
        }
        
    }

    @Override
    public PageResponse createPost(PostRequest post, String title) {
        final var pageToUpdate = this.pageRepository.findByTitle(title)
        .orElseThrow(() -> new IllegalArgumentException("Title not found"));

        final var postEntity = new PostEntity();
        BeanUtils.copyProperties(post, postEntity);
        postEntity.setDateCreation(LocalDateTime.now());

        pageToUpdate.addPost(postEntity);

        final var responseEntity = this.pageRepository.save(pageToUpdate);

        final var response = new PageResponse();
        BeanUtils.copyProperties(responseEntity, response);

        final List<PostResponse> postResponses = responseEntity.getPosts()
        .stream() //Convert to stream
        .map(postE ->
            PostResponse //Transforma postEntity into postResponse
                .builder()
                .img(postE.getImg())
                .content(postE.getContent())
                .dateCreation(postE.getDateCreation())
                .build()
        ).toList(); //Converts to list

        response.setPosts(postResponses);

        return null;
    }

    @Override
    public void deletePost(Long idPost, String title) {
        final var pageToUpdate = this.pageRepository.findByTitle(title)
        .orElseThrow(() -> new IllegalArgumentException("Title not found"));

        final var postToDelete = pageToUpdate.getPosts()
        .stream()
        .filter(post -> post.getId().equals(idPost))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("post id not found"));

        pageToUpdate.removePost(postToDelete);

        this.pageRepository.save(pageToUpdate);
    }

    private void validTitle(String title){
        if(title.contains("putitos") || title.contains("jotitos")){
            throw new TitleNotValidException("Title can't contain bad words");
        }
    }
    
}
