package com.example.spring_data_jpa_complex_object.dto;

import lombok.Data;

@Data
public class KitInfoDTO {
    // Getters and Setters
//    private Long id;
    private String cardType;
    private String cardCategory;
    private String cardStatus;
    private String aliasName;

}
