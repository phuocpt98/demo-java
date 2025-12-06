package com.phuocpt98.demo.entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Table(name = "users")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;

    @Column(name = "password_hash")
    String password;
    String email;
    @Column(name = "full_name")
    String fullName;

    @ManyToMany
    Set<Role> roles;
}
