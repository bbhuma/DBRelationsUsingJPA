package com.example.spring_data_jpa_complex_object.repository;

import com.example.spring_data_jpa_complex_object.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long> {
    List<AccountInfo> findByCustomerCustomerId(Long customerId);
    Optional<AccountInfo> findByCustomerCustomerIdAndId(Long customerId, Long accountId);
}