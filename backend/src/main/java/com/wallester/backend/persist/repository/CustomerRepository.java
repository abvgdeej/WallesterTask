package com.wallester.backend.persist.repository;

import com.wallester.backend.domain.dto.CustomerDto;
import com.wallester.backend.persist.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    List<CustomerEntity> findAllByFirstNameAndLastName(String firstName, String lastName);
}
