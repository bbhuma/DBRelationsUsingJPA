package com.example.spring_data_jpa_complex_object.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerCreateDTO {
//    private Long customerId;
    private String firstName;
    private String lastName;
    private String gender;
    private List<AddressCreateDTO> addressInfo;
    private List<AccountInfoCreateDTO> accountInfo;
    private List<CommunicationcCreateDTO> communicationInfo;
    private List<DOBCreateDTO> dateInfo;
    private List<KitInfoCreateDTO> kitInfo;


}