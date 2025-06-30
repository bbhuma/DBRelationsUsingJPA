package com.example.spring_data_jpa_complex_object.controller;

import com.example.spring_data_jpa_complex_object.dto.CustomerCreateDTO;
import com.example.spring_data_jpa_complex_object.dto.CustomerDTO;
import com.example.spring_data_jpa_complex_object.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        CustomerCreateDTO createDTO = new CustomerCreateDTO();
        CustomerDTO dto = new CustomerDTO();
        when(customerService.createCustomer(createDTO)).thenReturn(dto);
        ResponseEntity<CustomerDTO> response = customerController.createCustomer(createDTO);
        assertEquals(dto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetCustomer() {
        CustomerDTO dto = new CustomerDTO();
        when(customerService.getCustomer(1L)).thenReturn(dto);
        ResponseEntity<CustomerDTO> response = customerController.getCustomer(1L);
        assertEquals(dto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllCustomers() {
        List<CustomerDTO> list = Arrays.asList(new CustomerDTO(), new CustomerDTO());
        when(customerService.getAllCustomers()).thenReturn(list);
        ResponseEntity<List<CustomerDTO>> response = customerController.getAllCustomers();
        assertEquals(list, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateCustomer() {
        CustomerDTO dto = new CustomerDTO();
        when(customerService.updateCustomer(eq(1L), any(CustomerDTO.class))).thenReturn(dto);
        ResponseEntity<?> response = customerController.updateCustomer(1L, dto);
        assertEquals(dto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerService).deleteCustomer(1L);
        ResponseEntity<Void> response = customerController.deleteCustomer(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCleanDatabase() {
        doNothing().when(customerService).cleanDatabase();
        ResponseEntity<String> response = customerController.cleanDatabase();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Database cleaned.", response.getBody());
    }
}
