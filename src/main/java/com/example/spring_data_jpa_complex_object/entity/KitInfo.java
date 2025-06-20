package com.example.spring_data_jpa_complex_object.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "KitInfo")
public class KitInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @Column(name = "cardType")
    private String cardType;

    @Column(name = "cardCategory")
    private String cardCategory;

    @Column(name = "cardStatus")
    private String cardStatus;

    @Column(name = "aliasName")
    private String aliasName;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }
    public String getCardCategory() { return cardCategory; }
    public void setCardCategory(String cardCategory) { this.cardCategory = cardCategory; }
    public String getCardStatus() { return cardStatus; }
    public void setCardStatus(String cardStatus) { this.cardStatus = cardStatus; }
    public String getAliasName() { return aliasName; }
    public void setAliasName(String aliasName) { this.aliasName = aliasName; }
}
