package com.example.spring_data_jpa_complex_object.dto;

import lombok.Data;

@Data
public class AddressDTO {
    //private Long id;
    private String addressType;
    private String addressLine;
    private String city;
    private String pincode;

}
