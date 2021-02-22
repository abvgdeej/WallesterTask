package com.wallester.backend.service;

import com.wallester.backend.persist.repository.CustomerRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class CustomerService {
    private CustomerRepository repository;
}
