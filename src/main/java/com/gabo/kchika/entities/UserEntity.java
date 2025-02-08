package com.gabo.kchika.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.DialectOverride.GeneratedColumns;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AppUser")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String mail;
    private String password;
    private Integer age;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private PageEntity page;
}
