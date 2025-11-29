package com.phuocpt98.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String username;

    @Column(name = "password_hash")
    private String password;
    private String email;
    @Column(name = "full_name")
    private String fullName;
}
