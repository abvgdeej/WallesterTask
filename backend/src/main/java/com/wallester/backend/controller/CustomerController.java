package com.wallester.backend.controller;

import com.wallester.backend.controller.request.CustomerCreateRequest;
import com.wallester.backend.controller.response.CustomerEditResponse;
import com.wallester.backend.controller.response.CustomersResponse;
import com.wallester.backend.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("v1/customer")
public class CustomerController {
    private CustomerService service;

    @PostMapping("/")
    public ResponseEntity<?> createCustomer(CustomerCreateRequest request) {
        //todo
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<CustomerEditResponse> editCustomer() {
        //todo
        return new ResponseEntity<>(new CustomerEditResponse(), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<CustomersResponse> findCustomersByNames() {
        //todo
        return new ResponseEntity<>(new CustomersResponse(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<CustomersResponse> findAllCustomers() {
        //todo
        return new ResponseEntity<>(new CustomersResponse(), HttpStatus.OK);
    }
}
