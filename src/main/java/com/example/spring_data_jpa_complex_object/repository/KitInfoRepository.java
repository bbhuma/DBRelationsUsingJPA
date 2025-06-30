package com.example.spring_data_jpa_complex_object.repository;

import com.example.spring_data_jpa_complex_object.entity.KitInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KitInfoRepository extends JpaRepository<KitInfo, Long> {
    List<KitInfo> findByCustomerCustomerId(Long customerId);
}