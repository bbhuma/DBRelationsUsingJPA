package com.example.spring_data_jpa_complex_object.controller.child_controllers;

import com.example.spring_data_jpa_complex_object.entity.Customer;
import com.example.spring_data_jpa_complex_object.entity.KitInfo;
import com.example.spring_data_jpa_complex_object.repository.CustomerRepository;
import com.example.spring_data_jpa_complex_object.repository.KitInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/kits")
public class KitInfoController {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private KitInfoRepository kitRepo;

    @PostMapping
    public KitInfo addKit(@PathVariable Long customerId, @RequestBody KitInfo kit) {
        Customer customer = customerRepo.findById(customerId).orElseThrow();
        kit.setCustomer(customer);
        return kitRepo.save(kit);
    }

    @GetMapping
    public List<KitInfo> getAll(@PathVariable Long customerId) {
        return kitRepo.findByCustomerCustomerId(customerId);
    }

    @PutMapping("/{id}")
    public KitInfo update(@PathVariable Long id, @RequestBody KitInfo updated) {
        KitInfo k = kitRepo.findById(id).orElseThrow();
        k.setCardType(updated.getCardType());
        k.setCardCategory(updated.getCardCategory());
        k.setCardStatus(updated.getCardStatus());
        k.setAliasName(updated.getAliasName());
        return kitRepo.save(k);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        kitRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
