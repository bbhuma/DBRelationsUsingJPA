package com.example.spring_data_jpa_complex_object.controller.child_controllers;

import com.example.spring_data_jpa_complex_object.entity.Customer;
import com.example.spring_data_jpa_complex_object.entity.DOB;
import com.example.spring_data_jpa_complex_object.repository.CustomerRepository;
import com.example.spring_data_jpa_complex_object.repository.DOBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/dob")
public class DOBController {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private DOBRepository dobRepo;

    @PostMapping
    public DOB addDob(@PathVariable Long customerId, @RequestBody DOB dob) {
        Customer customer = customerRepo.findById(customerId).orElseThrow();
        dob.setCustomer(customer);
        return dobRepo.save(dob);
    }

    @GetMapping
    public List<DOB> getAll(@PathVariable Long customerId) {
        return dobRepo.findByCustomerCustomerId(customerId);
    }

    @PutMapping("/{id}")
    public DOB update(@PathVariable Long id, @RequestBody DOB updated) {
        DOB dob = dobRepo.findById(id).orElseThrow();
        dob.setBirthDate(updated.getBirthDate());
        return dobRepo.save(dob);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dobRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
