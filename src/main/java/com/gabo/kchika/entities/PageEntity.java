package com.gabo.kchika.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Page")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDateTime dateCreation;

    @OneToOne()
    @JoinColumn(name = "id_User", unique = true) //FK 
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_page") //FK
    private List<PostEntity> posts = new ArrayList<>();

    public void addPost(PostEntity post){
        posts.add(post);
    }
    public void removePost(PostEntity post){
        posts.remove(post);
    }
}
