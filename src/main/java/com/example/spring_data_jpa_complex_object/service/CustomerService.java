package com.example.spring_data_jpa_complex_object.service;

import com.example.spring_data_jpa_complex_object.dto.*;
import com.example.spring_data_jpa_complex_object.entity.*;
import com.example.spring_data_jpa_complex_object.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Create
    public CustomerDTO createCustomer(CustomerCreateDTO dto) {
        Customer customer = new Customer();
//        customer.setCustomerId(dto.getCustomerId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setGender(dto.getGender());

        // Addresses
        if (dto.getAddressInfo() != null) {
            Customer finalCustomer = customer;
            List<Address> addresses = dto.getAddressInfo().stream().map(adto -> {
                Address addr = new Address();
//                addr.setId(adto.getId());
                addr.setAddressType(adto.getAddressType());
                addr.setAddressLine(adto.getAddressLine());
                addr.setCity(adto.getCity());
                addr.setPincode(adto.getPincode());
                addr.setCustomer(finalCustomer);
                return addr;
            }).collect(Collectors.toList());
            customer.setAddresses(addresses);
        }

        // Accounts
        if (dto.getAccountInfo() != null) {
            Customer finalCustomer1 = customer;
            List<AccountInfo> accounts = dto.getAccountInfo().stream().map(acdto -> {
                AccountInfo acc = new AccountInfo();
//                acc.setId(acdto.getId());
                acc.setAccountNo(acdto.getAccountNo());
                acc.setAccountType(acdto.getAccountType());
                acc.setCurrency(acdto.getCurrency());
                acc.setBranchId(acdto.getBranchId());
                acc.setCustomer(finalCustomer1);
                return acc;
            }).collect(Collectors.toList());
            customer.setAccounts(accounts);
        }

        // Communications
        if (dto.getCommunicationInfo() != null) {
            Customer finalCustomer2 = customer;
            List<Communication> comms = dto.getCommunicationInfo().stream().map(cdto -> {
                Communication comm = new Communication();
//                comm.setId(cdto.getId());
                comm.setPhone(cdto.getPhone());
                comm.setEmail(cdto.getEmail());
                comm.setCustomer(finalCustomer2);
                return comm;
            }).collect(Collectors.toList());
            customer.setCommunications(comms);
        }

        // DOBs
        if (dto.getDateInfo() != null) {
            Customer finalCustomer3 = customer;
            List<DOB> dobs = dto.getDateInfo().stream().map(ddto -> {
                DOB dob = new DOB();
//                dob.setId(ddto.getId());
                dob.setBirthDate(ddto.getBirthDate());
                dob.setCustomer(finalCustomer3);
                return dob;
            }).collect(Collectors.toList());
            customer.setDobs(dobs);
        }

        // Kits
        if (dto.getKitInfo() != null) {
            Customer finalCustomer4 = customer;
            List<KitInfo> kits = dto.getKitInfo().stream().map(kdto -> {
                KitInfo kit = new KitInfo();
//                kit.setId(kdto.getId());
                kit.setCardType(kdto.getCardType());
                kit.setCardCategory(kdto.getCardCategory());
                kit.setCardStatus(kdto.getCardStatus());
                kit.setAliasName(kdto.getAliasName());
                kit.setCustomer(finalCustomer4);
                return kit;
            }).collect(Collectors.toList());
            customer.setKits(kits);
        }

        customer = customerRepository.save(customer);
        return toDTO(customer);
    }

    // Read (by ID)
    public CustomerDTO getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return toDTO(customer);
    }

    // Read (all)
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Update
    public CustomerDTO updateCustomer(Long customerId, CustomerDTO dto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setGender(dto.getGender());

        // Clear existing relationships






        // Update Addresses
        if (dto.getAddressInfo() != null) {
            customer.getAddresses().clear(); // ✅ Clear the managed collection

            for (AddressDTO adto : dto.getAddressInfo()) {
                Address addr = new Address();
                addr.setId(adto.getId()); // Optional, if you support updating existing addresses
                addr.setAddressType(adto.getAddressType());
                addr.setAddressLine(adto.getAddressLine());
                addr.setCity(adto.getCity());
                addr.setPincode(adto.getPincode());
                addr.setCustomer(customer); // ✅ Set back-reference
                customer.getAddresses().add(addr); // ✅ Add to the existing list
            }
        }
    /*
        // Update Accounts
        if (dto.getAccountInfo() != null) {
            customer.getAccounts().clear();
            Customer finalCustomer1 = customer;
            List<AccountInfo> accounts = dto.getAccountInfo().stream().map(acdto -> {
                AccountInfo acc = new AccountInfo();
                acc.setId(acdto.getId());
                acc.setAccountNo(acdto.getAccountNo());
                acc.setAccountType(acdto.getAccountType());
                acc.setCurrency(acdto.getCurrency());
                acc.setBranchId(acdto.getBranchId());
                acc.setCustomer(finalCustomer1);
                return acc;
            }).collect(Collectors.toList());
            customer.setAccounts(accounts);
        }

        // Update Communications
        if (dto.getCommunicationInfo() != null) {
            customer.getCommunications().clear();
            Customer finalCustomer2 = customer;
            List<Communication> comms = dto.getCommunicationInfo().stream().map(cdto -> {
                Communication comm = new Communication();
                comm.setId(cdto.getId());
                comm.setPhone(cdto.getPhone());
                comm.setEmail(cdto.getEmail());
                comm.setCustomer(finalCustomer2);
                return comm;
            }).collect(Collectors.toList());
            customer.setCommunications(comms);
        }

        // Update DOBs
        if (dto.getDateInfo() != null) {
            customer.getDobs().clear();
            Customer finalCustomer3 = customer;
            List<DOB> dobs = dto.getDateInfo().stream().map(ddto -> {
                DOB dob = new DOB();
                dob.setId(ddto.getId());
                dob.setBirthDate(ddto.getBirthDate());
                dob.setCustomer(finalCustomer3);
                return dob;
            }).collect(Collectors.toList());
            customer.setDobs(dobs);
        }

        // Update Kits
        if (dto.getKitInfo() != null) {
            customer.getKits().clear();
            Customer finalCustomer4 = customer;
            List<KitInfo> kits = dto.getKitInfo().stream().map(kdto -> {
                KitInfo kit = new KitInfo();
                kit.setId(kdto.getId());
                kit.setCardType(kdto.getCardType());
                kit.setCardCategory(kdto.getCardCategory());
                kit.setCardStatus(kdto.getCardStatus());
                kit.setAliasName(kdto.getAliasName());
                kit.setCustomer(finalCustomer4);
                return kit;
            }).collect(Collectors.toList());
            customer.setKits(kits);
        }
        */
        // Update Accounts
        if (dto.getAccountInfo() != null) {
            customer.getAccounts().clear();
            for (AccountInfoDTO acdto : dto.getAccountInfo()) {
                AccountInfo acc = new AccountInfo();
                acc.setId(acdto.getId()); // Optional: only if you support updating existing records
                acc.setAccountNo(acdto.getAccountNo());
                acc.setAccountType(acdto.getAccountType());
                acc.setCurrency(acdto.getCurrency());
                acc.setBranchId(acdto.getBranchId());
                acc.setCustomer(customer);
                customer.getAccounts().add(acc); // ✅ Add to existing collection
            }
        }

// Update Communications
        if (dto.getCommunicationInfo() != null) {
            customer.getCommunications().clear();
            for (CommunicationDTO cdto : dto.getCommunicationInfo()) {
                Communication comm = new Communication();
                comm.setId(cdto.getId());
                comm.setPhone(cdto.getPhone());
                comm.setEmail(cdto.getEmail());
                comm.setCustomer(customer);
                customer.getCommunications().add(comm);
            }
        }

// Update DOBs
        if (dto.getDateInfo() != null) {
            customer.getDobs().clear();
            for (DOBDTO ddto : dto.getDateInfo()) {
                DOB dob = new DOB();
                dob.setId(ddto.getId());
                dob.setBirthDate(ddto.getBirthDate());
                dob.setCustomer(customer);
                customer.getDobs().add(dob);
            }
        }

// Update Kits
        if (dto.getKitInfo() != null) {
            customer.getKits().clear();
            for (KitInfoDTO kdto : dto.getKitInfo()) {
                KitInfo kit = new KitInfo();
                kit.setId(kdto.getId());
                kit.setCardType(kdto.getCardType());
                kit.setCardCategory(kdto.getCardCategory());
                kit.setCardStatus(kdto.getCardStatus());
                kit.setAliasName(kdto.getAliasName());
                kit.setCustomer(customer);
                customer.getKits().add(kit);
            }
        }


        customer = customerRepository.save(customer);
        return toDTO(customer);
    }

    // Delete
    public void deleteCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new RuntimeException("Customer not found");
        }
        customerRepository.deleteById(customerId);
    }

    // Convert Entity to DTO
    private CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setGender(customer.getGender());

        // Addresses
        dto.setAddressInfo(customer.getAddresses().stream().map(a -> {
            AddressDTO adto = new AddressDTO();
            adto.setId(a.getId());
            adto.setAddressType(a.getAddressType());
            adto.setAddressLine(a.getAddressLine());
            adto.setCity(a.getCity());
            adto.setPincode(a.getPincode());
            return adto;
        }).collect(Collectors.toList()));

        // Accounts
        dto.setAccountInfo(customer.getAccounts().stream().map(ai -> {
            AccountInfoDTO aidto = new AccountInfoDTO();
            aidto.setId(ai.getId());
            aidto.setAccountNo(ai.getAccountNo());
            aidto.setAccountType(ai.getAccountType());
            aidto.setCurrency(ai.getCurrency());
            aidto.setBranchId(ai.getBranchId());
            return aidto;
        }).collect(Collectors.toList()));

        // Communications
        dto.setCommunicationInfo(customer.getCommunications().stream().map(c -> {
            CommunicationDTO cdto = new CommunicationDTO();
            cdto.setId(c.getId());
            cdto.setPhone(c.getPhone());
            cdto.setEmail(c.getEmail());
            return cdto;
        }).collect(Collectors.toList()));

        // DOBs
        dto.setDateInfo(customer.getDobs().stream().map(d -> {
            DOBDTO ddto = new DOBDTO();
            ddto.setId(d.getId());
            ddto.setBirthDate(d.getBirthDate());
            return ddto;
        }).collect(Collectors.toList()));

        // Kits
        dto.setKitInfo(customer.getKits().stream().map(k -> {
            KitInfoDTO kdto = new KitInfoDTO();
            kdto.setId(k.getId());
            kdto.setCardType(k.getCardType());
            kdto.setCardCategory(k.getCardCategory());
            kdto.setCardStatus(k.getCardStatus());
            kdto.setAliasName(k.getAliasName());
            return kdto;
        }).collect(Collectors.toList()));

        return dto;
    }
}
