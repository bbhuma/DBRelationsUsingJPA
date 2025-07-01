package com.example.spring_data_jpa_complex_object.entity;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {
    public static final String ADMIN = "ADMIN";
    public static final String TEACHER = "TEACHER";
    public static final String STUDENT = "STUDENT";
    public static final String PARENT = "PARENT";
    public static final String REGISTRAR = "REGISTRAR";
    public static final String GUEST = "GUEST";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // ADMIN, TEACHER, STUDENT, PARENT, REGISTRAR, GUEST

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
