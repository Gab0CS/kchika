package com.gabo.kchika.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gabo.kchika.dtos.PageResponse;
import com.gabo.kchika.entities.PageEntity;
import com.gabo.kchika.entities.PostEntity;
import com.gabo.kchika.repositories.PageRepository;
import com.gabo.kchika.repositories.UserRepository;
import com.gabo.kchika.services.PageServiceImpl;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
public class PageServiceTest {

    @SuppressWarnings("removal")
    @MockBean //Similar to Autowired
    private PageRepository pageRepository;
    private UserRepository userRepository;

    @Autowired
    private PageServiceImpl target;

    //Test of type void always    
    @Test //Happy path cause everything goes fine
    void readByTitle_ShouldReturnPageResponse_WhenTitleExist(){
        String title = "gabo jeje";
        PostEntity postEntity = new PostEntity();

        postEntity.setImg("http://img");
        postEntity.setContent("Some content");
        postEntity.setDateCreation(LocalDateTime.MIN);

        PageEntity pageEntity = new PageEntity();
        pageEntity.setTitle(title);
        pageEntity.setDateCreation(LocalDateTime.MIN);
        pageEntity.setPosts(List.of(postEntity));

        given(pageRepository.findByTitle(title))
        .willReturn(Optional.of(pageEntity));

        PageResponse result = target.readByTitle(title);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("gabo jeje");
        assertThat(result.getDateCreation()).isEqualTo(LocalDateTime.MIN);
        assertThat(result.getPosts()).hasSize(2);
    }
    //Test of type void always    
    @Test //Unhappy path 
    void readByTitle_ShouldReturnException_WhenTitleExist(){
        String title = "Invalid title";

        given(pageRepository.findByTitle(title))
        .willReturn(Optional.empty()); 

        assertThatThrownBy(() -> target.readByTitle(title))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Title not found");

        verify(pageRepository).findByTitle(title);
    }
}
