package com.wallester.backend.service;

import com.wallester.backend.domain.dto.CustomerDto;
import com.wallester.backend.domain.mapper.CustomerMapper;
import com.wallester.backend.exception.ApiException;
import com.wallester.backend.persist.entity.CustomerEntity;
import com.wallester.backend.persist.repository.CustomerRepository;
import lombok.Data;
import org.apache.tomcat.jni.Local;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
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
        LocalDate date = dto.getBirthDate().toLocalDate();
        LocalDate currentDate = LocalDate.now();
        int difference = Period.between(date, currentDate).getYears();
        if (difference < 18 || difference > 60) {
            throw new ApiException("Customer's age must be between 18 and 60", 400);
        }

        CustomerEntity entity = mapper.mapToEntity(dto);
        repository.save(entity);
    }

    /**
     * Updates customer with received id if he presents in DB.<br>
     * This operation executes in transaction.
     *
     * @param id    customer identifier in DB
     * @param dto   customer DTO
     * @return      updated customer DTO
     */
    @Transactional
    public CustomerDto editCustomer(int id, CustomerDto dto) {
        Optional<CustomerEntity> optional = repository.findById(id);
        if (optional.isPresent()) {
            CustomerEntity entity = optional.get();
            CustomerEntity newEntity =  mapper.mapToEntity(dto);
            newEntity.setId(entity.getId());
            repository.save(newEntity);
            return mapper.mapToDto(newEntity);
        } else {
            throw ApiException.badRequestError("There is no customer with id = " + id);
        }
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
