package com.example.spring_data_jpa_complex_object.controller;

import com.example.spring_data_jpa_complex_object.dto.CustomerCreateDTO;
import com.example.spring_data_jpa_complex_object.dto.CustomerDTO;
import com.example.spring_data_jpa_complex_object.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerCreateDTO customerDTO) {
        CustomerDTO created = customerService.createCustomer(customerDTO);
        return ResponseEntity.ok(created);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long customerId) {
        CustomerDTO customerDTO = customerService.getCustomer(customerId);
        return ResponseEntity.ok(customerDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updated = customerService.updateCustomer(customerId, customerDTO);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/cleanup")
    public ResponseEntity<String> cleanDatabase() {
        // Delete child tables first to avoid FK constraint violations
     customerService.cleanDatabase();
        return ResponseEntity.ok("Database cleaned.");
    }


}
