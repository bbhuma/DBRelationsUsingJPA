package com.example.spring_data_jpa_complex_object.dto;

import java.util.List;

public class CustomerDTO {
    private String customerId;
    private String firstName;
    private String lastName;
    private String gender;
    private List<AddressDTO> addressInfo;
    private List<AccountInfoDTO> accountInfo;
    private List<CommunicationDTO> communicationInfo;
    private List<DOBDTO> dateInfo;
    private List<KitInfoDTO> kitInfo;

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public List<AddressDTO> getAddressInfo() { return addressInfo; }
    public void setAddressInfo(List<AddressDTO> addressInfo) { this.addressInfo = addressInfo; }
    public List<AccountInfoDTO> getAccountInfo() { return accountInfo; }
    public void setAccountInfo(List<AccountInfoDTO> accountInfo) { this.accountInfo = accountInfo; }
    public List<CommunicationDTO> getCommunicationInfo() { return communicationInfo; }
    public void setCommunicationInfo(List<CommunicationDTO> communicationInfo) { this.communicationInfo = communicationInfo; }
    public List<DOBDTO> getDateInfo() { return dateInfo; }
    public void setDateInfo(List<DOBDTO> dateInfo) { this.dateInfo = dateInfo; }
    public List<KitInfoDTO> getKitInfo() { return kitInfo; }
    public void setKitInfo(List<KitInfoDTO> kitInfo) { this.kitInfo = kitInfo; }
}