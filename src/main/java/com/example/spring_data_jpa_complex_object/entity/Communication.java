package com.example.spring_data_jpa_complex_object.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Communication")
public class Communication {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

}
