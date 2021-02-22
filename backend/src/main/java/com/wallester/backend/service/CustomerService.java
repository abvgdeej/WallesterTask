package com.wallester.backend.service;

import com.wallester.backend.domain.dto.CustomerDto;
import com.wallester.backend.domain.mapper.CustomerMapper;
import com.wallester.backend.persist.entity.CustomerEntity;
import com.wallester.backend.persist.repository.CustomerRepository;
import lombok.Data;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class CustomerService {
    private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates customer in DB
     * @param dto customer DTO from request
     */
    public void createCustomer(CustomerDto dto) {
        CustomerEntity entity = mapper.mapToEntity(dto);
        repository.save(entity);
    }

    @Transactional
    public CustomerDto editCustomer(int id, CustomerDto dto) {
        //todo
        return null;
    }

    /**
     * Returns all customers with matched names
     *
     * @param firstName customer's first name
     * @param lastName  customer's last name
     * @return          all matches
     */
    public List<CustomerDto> findByFirstNameAndLastName(String firstName, String lastName) {
        return repository.findAllByFirstNameAndLastName(firstName, lastName).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * @return all {@link CustomerEntity}
     */
    public List<CustomerDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
