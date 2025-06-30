package com.example.spring_data_jpa_complex_object.controller.child_controllers;

import com.example.spring_data_jpa_complex_object.entity.Communication;
import com.example.spring_data_jpa_complex_object.entity.Customer;
import com.example.spring_data_jpa_complex_object.repository.CommunicationRepository;
import com.example.spring_data_jpa_complex_object.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/communications")
public class CommunicationController {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private CommunicationRepository communicationRepo;

    @PostMapping
    public Communication addCommunication(@PathVariable Long customerId, @RequestBody Communication communication) {
        Customer customer = customerRepo.findById(customerId).orElseThrow();
        communication.setCustomer(customer);
        return communicationRepo.save(communication);
    }

    @GetMapping
    public List<Communication> getAll(@PathVariable Long customerId) {
        return communicationRepo.findByCustomerCustomerId(customerId);
    }

    @PutMapping("/{id}")
    public Communication update(@PathVariable Long id, @RequestBody Communication updated) {
        Communication c = communicationRepo.findById(id).orElseThrow();
        c.setPhone(updated.getPhone());
        c.setEmail(updated.getEmail());
        return communicationRepo.save(c);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        communicationRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
