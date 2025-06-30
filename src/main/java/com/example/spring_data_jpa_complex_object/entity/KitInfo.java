package com.example.spring_data_jpa_complex_object.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Entity
@Table(name = "KitInfo")
public class KitInfo {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    @JsonBackReference
    private Customer customer;

    @Column(name = "cardType")
    private String cardType;

    @Column(name = "cardCategory")
    private String cardCategory;

    @Column(name = "cardStatus")
    private String cardStatus;

    @Column(name = "aliasName")
    private String aliasName;

}
