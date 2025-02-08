package com.gabo.kchika.services;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabo.kchika.dtos.PageRequest;
import com.gabo.kchika.dtos.PageResponse;
import com.gabo.kchika.dtos.PostRequest;
import com.gabo.kchika.entities.PageEntity;
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
        return null;
    }

    @Override
    public PageResponse update(PageRequest page, String title) {
        return null;
    }

    @Override
    public void delete(String title) {

    }

    @Override
    public PageResponse createPost(PostRequest post) {
        return null;
    }

    @Override
    public PageResponse deletePost(Long idPost) {
        return null;
    }
    
}
