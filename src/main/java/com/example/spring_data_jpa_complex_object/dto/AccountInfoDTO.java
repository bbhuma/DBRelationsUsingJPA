package com.example.spring_data_jpa_complex_object.dto;

import lombok.Data;

@Data
public class AccountInfoDTO {
// Getters and Setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
    //    private Long id;
    private String accountNo;
    private String accountType;
    private String currency;
    private String branchId;

}
