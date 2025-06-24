package com.example.spring_data_jpa_complex_object.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Address")
public class Address {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @Column(name = "addressType")
    private String addressType;

    @Column(name = "addressLine")
    private String addressLine;

    @Column(name = "city")
    private String city;

    @Column(name = "pincode")
    private String pincode;

}
