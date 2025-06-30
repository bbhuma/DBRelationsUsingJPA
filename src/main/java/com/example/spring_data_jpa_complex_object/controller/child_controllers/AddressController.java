package com.example.spring_data_jpa_complex_object.controller.child_controllers;

import com.example.spring_data_jpa_complex_object.entity.Address;
import com.example.spring_data_jpa_complex_object.entity.Customer;
import com.example.spring_data_jpa_complex_object.repository.AddressRepository;
import com.example.spring_data_jpa_complex_object.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/addresses")
public class AddressController {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private AddressRepository addressRepo;

    @PostMapping
    public Address addAddress(@PathVariable Long customerId, @RequestBody Address address) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        address.setCustomer(customer);
        return addressRepo.save(address);
    }

    @GetMapping
    public List<Address> getAddresses(@PathVariable Long customerId) {
        return addressRepo.findByCustomerCustomerId(customerId);
    }

    @PutMapping("/{addressId}")
    public Address update(@PathVariable Long addressId, @RequestBody Address updated) {
        Address addr = addressRepo.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        addr.setAddressType(updated.getAddressType());
        addr.setAddressLine(updated.getAddressLine());
        addr.setCity(updated.getCity());
        addr.setPincode(updated.getPincode());

        return addressRepo.save(addr);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> delete(@PathVariable Long addressId) {
        addressRepo.deleteById(addressId);
        return ResponseEntity.noContent().build();
    }
}
