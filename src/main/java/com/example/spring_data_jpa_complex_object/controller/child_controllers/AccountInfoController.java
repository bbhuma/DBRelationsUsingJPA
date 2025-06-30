package com.example.spring_data_jpa_complex_object.controller.child_controllers;

import com.example.spring_data_jpa_complex_object.entity.AccountInfo;
import com.example.spring_data_jpa_complex_object.entity.Customer;
import com.example.spring_data_jpa_complex_object.repository.AccountInfoRepository;
import com.example.spring_data_jpa_complex_object.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/accounts")
public class AccountInfoController {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private AccountInfoRepository accountRepo;

    // @PostMapping
    // public ResponseEntity<AccountInfo> addAccount(@PathVariable Long customerId,
    //                                               @RequestBody AccountInfo accountInfo) {
    //     Customer customer = customerRepo.findById(customerId)
    //             .orElseThrow(() -> new RuntimeException("Customer not found"));
    //     accountInfo.setCustomer(customer);
    //     return new ResponseEntity<>(accountRepo.save(accountInfo), HttpStatus.CREATED);
    // }
    // 1. Get all accounts of a customer
    @GetMapping
    public List<AccountInfo> getAccounts(@PathVariable Long customerId) {
        return accountRepo.findByCustomerCustomerId(customerId);
    }
    // 2. Get specific account of a customer
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountInfo> getAccountById(
            @PathVariable Long customerId,
            @PathVariable Long accountId) {

        return accountRepo.findByCustomerCustomerIdAndId(customerId, accountId)
                .map(ResponseEntity::ok) //.map(account -> ResponseEntity.ok(account))
                .orElse(ResponseEntity.notFound().build());
    }
    // 3. Create new account for a customer
    @PostMapping
    public ResponseEntity<AccountInfo> createAccount(
            @PathVariable Long customerId,
            @RequestBody AccountInfo accountInfo) {

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        accountInfo.setCustomer(customer);
        AccountInfo saved = accountRepo.save(accountInfo);
        return ResponseEntity.ok(saved);
    }

    // 4. Update account of a customer
    @PutMapping("/{accountId}")
    public ResponseEntity<AccountInfo> updateAccount(
            @PathVariable Long customerId,
            @PathVariable Long accountId,
            @RequestBody AccountInfo updatedAccount) {

        return accountRepo.findByCustomerCustomerIdAndId(customerId, accountId)
                .map(existing -> {
                    existing.setAccountNo(updatedAccount.getAccountNo());
                    existing.setAccountType(updatedAccount.getAccountType());
                    existing.setCurrency(updatedAccount.getCurrency());
                    existing.setBranchId(updatedAccount.getBranchId());
                    return ResponseEntity.ok(accountRepo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    // 5. Delete account of a customer
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Object> deleteAccount(
            @PathVariable Long customerId,
            @PathVariable Long accountId) {

        return accountRepo.findByCustomerCustomerIdAndId(customerId, accountId)
                .map(acc -> {
                    accountRepo.delete(acc);
                    return ResponseEntity.noContent().build(); // return 204
                })
                .orElse(ResponseEntity.notFound().build()); // return 404
    }
}


  /*  @PutMapping("/{accountId}")
    public AccountInfo updateAccount(@PathVariable Long accountId, @RequestBody AccountInfo updated) {
        AccountInfo acc = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        acc.setAccountNo(updated.getAccountNo());
        acc.setAccountType(updated.getAccountType());
        acc.setCurrency(updated.getCurrency());
        acc.setBranchId(updated.getBranchId());

        return accountRepo.save(acc);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountRepo.deleteById(accountId);
        return ResponseEntity.noContent().build();
    }
}*/
