package com.example.javaspringauthentication.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@Accessors(chain = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String lastname;

    private String firstname;

    private String password;

//    private String role;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

}
