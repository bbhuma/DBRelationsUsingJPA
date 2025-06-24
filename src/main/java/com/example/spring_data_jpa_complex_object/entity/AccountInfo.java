package com.example.spring_data_jpa_complex_object.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "AccountInfo")
public class AccountInfo {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @Column(name = "accountNo", nullable = false)
    private String accountNo;

    @Column(name = "accountType")
    private String accountType;

    @Column(name = "currency")
    private String currency;

    @Column(name = "branchId")
    private String branchId;

}
