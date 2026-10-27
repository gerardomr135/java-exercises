package com.epam.practice.jpa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String email;

    // Try fetch = FetchType.EAGER
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = {ALL})
    private List<Address> addresses = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Genre genre;
}
