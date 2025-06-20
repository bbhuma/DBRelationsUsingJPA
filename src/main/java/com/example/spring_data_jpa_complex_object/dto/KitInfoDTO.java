package com.example.spring_data_jpa_complex_object.dto;


public class KitInfoDTO {
    private Long id;
    private String cardType;
    private String cardCategory;
    private String cardStatus;
    private String aliasName;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }
    public String getCardCategory() { return cardCategory; }
    public void setCardCategory(String cardCategory) { this.cardCategory = cardCategory; }
    public String getCardStatus() { return cardStatus; }
    public void setCardStatus(String cardStatus) { this.cardStatus = cardStatus; }
    public String getAliasName() { return aliasName; }
    public void setAliasName(String aliasName) { this.aliasName = aliasName; }
}
