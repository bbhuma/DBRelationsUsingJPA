package com.example.spring_data_jpa_complex_object.mapper;

import com.example.spring_data_jpa_complex_object.dto.*;
import com.example.spring_data_jpa_complex_object.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    @Autowired
    private ModelMapper modelMapper;

    // -------------------- Entity to DTO --------------------

    public CustomerDTO toDto(Customer customer) {
        // Manual mapping (commented out for ModelMapper usage)
        /*
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setGender(customer.getGender());
        dto.setAddressInfo(customer.getAddresses() != null ?
                customer.getAddresses().stream().map(this::toDto).collect(Collectors.toList()) : null);
        dto.setAccountInfo(customer.getAccounts() != null ?
                customer.getAccounts().stream().map(this::toDto).collect(Collectors.toList()) : null);
        dto.setCommunicationInfo(customer.getCommunications() != null ?
                customer.getCommunications().stream().map(this::toDto).collect(Collectors.toList()) : null);
        dto.setDateInfo(customer.getDobs() != null ?
                customer.getDobs().stream().map(this::toDto).collect(Collectors.toList()) : null);
        dto.setKitInfo(customer.getKits() != null ?
                customer.getKits().stream().map(this::toDto).collect(Collectors.toList()) : null);
        return dto;
        */
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public AddressDTO toDto(Address entity) {
        AddressDTO dto = new AddressDTO();
        dto.setId(entity.getId());
        dto.setAddressType(entity.getAddressType());
        dto.setAddressLine(entity.getAddressLine());
        dto.setCity(entity.getCity());
        dto.setPincode(entity.getPincode());
        return dto;
    }

    public AccountInfoDTO toDto(AccountInfo entity) {
        AccountInfoDTO dto = new AccountInfoDTO();
        dto.setId(entity.getId());
        dto.setAccountNo(entity.getAccountNo());
        dto.setAccountType(entity.getAccountType());
        dto.setCurrency(entity.getCurrency());
        dto.setBranchId(entity.getBranchId());
        return dto;
    }

    public CommunicationDTO toDto(Communication entity) {
        CommunicationDTO dto = new CommunicationDTO();
        dto.setId(entity.getId());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        return dto;
    }

    public DOBDTO toDto(DOB entity) {
        DOBDTO dto = new DOBDTO();
        dto.setId(entity.getId());
        dto.setBirthDate(entity.getBirthDate());
        return dto;
    }

    public KitInfoDTO toDto(KitInfo entity) {
        KitInfoDTO dto = new KitInfoDTO();
        dto.setId(entity.getId());
        dto.setCardType(entity.getCardType());
        dto.setCardCategory(entity.getCardCategory());
        dto.setCardStatus(entity.getCardStatus());
        dto.setAliasName(entity.getAliasName());
        return dto;
    }

    // -------------------- DTO to Entity --------------------

    public Customer toEntity(CustomerDTO dto) {
        // Manual mapping (commented out for ModelMapper usage)
        /*
        Customer customer = new Customer();
        customer.setCustomerId(dto.getCustomerId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setGender(dto.getGender());
        if (dto.getAddressInfo() != null) {
            List<Address> addresses = dto.getAddressInfo().stream().map(addressDto -> {
                Address a = toEntity(addressDto);
                a.setCustomer(customer); // set the back-reference
                return a;
            }).collect(Collectors.toList());
            customer.setAddresses(addresses);
        }
        if (dto.getAccountInfo() != null) {
            List<AccountInfo> accounts = dto.getAccountInfo().stream().map(accountDto -> {
                AccountInfo a = toEntity(accountDto);
                a.setCustomer(customer);
                return a;
            }).collect(Collectors.toList());
            customer.setAccounts(accounts);
        }
        if (dto.getCommunicationInfo() != null) {
            List<Communication> comms = dto.getCommunicationInfo().stream().map(commDto -> {
                Communication c = toEntity(commDto);
                c.setCustomer(customer);
                return c;
            }).collect(Collectors.toList());
            customer.setCommunications(comms);
        }
        if (dto.getDateInfo() != null) {
            List<DOB> dobs = dto.getDateInfo().stream().map(dobDto -> {
                DOB d = toEntity(dobDto);
                d.setCustomer(customer);
                return d;
            }).collect(Collectors.toList());
            customer.setDobs(dobs);
        }
        if (dto.getKitInfo() != null) {
            List<KitInfo> kits = dto.getKitInfo().stream().map(kitDto -> {
                KitInfo k = toEntity(kitDto);
                k.setCustomer(customer);
                return k;
            }).collect(Collectors.toList());
            customer.setKits(kits);
        }
        return customer;
        */
        return modelMapper.map(dto, Customer.class);
    }

    public Address toEntity(AddressDTO dto) {
        Address address = new Address();
        address.setId(dto.getId());
        address.setAddressType(dto.getAddressType());
        address.setAddressLine(dto.getAddressLine());
        address.setCity(dto.getCity());
        address.setPincode(dto.getPincode());
        return address;
    }

    public AccountInfo toEntity(AccountInfoDTO dto) {
        AccountInfo account = new AccountInfo();
        account.setId(dto.getId());
        account.setAccountNo(dto.getAccountNo());
        account.setAccountType(dto.getAccountType());
        account.setCurrency(dto.getCurrency());
        account.setBranchId(dto.getBranchId());
        return account;
    }

    public Communication toEntity(CommunicationDTO dto) {
        Communication communication = new Communication();
        communication.setId(dto.getId());
        communication.setPhone(dto.getPhone());
        communication.setEmail(dto.getEmail());
        return communication;
    }

    public DOB toEntity(DOBDTO dto) {
        DOB dob = new DOB();
        dob.setId(dto.getId());
        dob.setBirthDate(dto.getBirthDate());
        return dob;
    }

    public KitInfo toEntity(KitInfoDTO dto) {
        KitInfo kit = new KitInfo();
        kit.setId(dto.getId());
        kit.setCardType(dto.getCardType());
        kit.setCardCategory(dto.getCardCategory());
        kit.setCardStatus(dto.getCardStatus());
        kit.setAliasName(dto.getAliasName());
        return kit;
    }
}
