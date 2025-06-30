package com.example.spring_data_jpa_complex_object.repository;

import com.example.spring_data_jpa_complex_object.entity.DOB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DOBRepository extends JpaRepository<DOB, Long> {
    List<DOB> findByCustomerCustomerId(Long customerId);
}