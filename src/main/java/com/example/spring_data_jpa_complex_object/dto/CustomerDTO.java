package com.example.spring_data_jpa_complex_object.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO {
//    private String customerId;
    private String firstName;
    private String lastName;
    private String gender;
    private List<AddressDTO> addressInfo;
    private List<AccountInfoDTO> accountInfo;
    private List<CommunicationDTO> communicationInfo;
    private List<DOBDTO> dateInfo;
    private List<KitInfoDTO> kitInfo;


}