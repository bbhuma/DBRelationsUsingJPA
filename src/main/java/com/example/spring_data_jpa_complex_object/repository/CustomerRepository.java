package com.example.spring_data_jpa_complex_object.repository;

import com.example.spring_data_jpa_complex_object.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}