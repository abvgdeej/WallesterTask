package com.wallester.backend.service;

import com.wallester.backend.controller.response.CustomersResponse;
import com.wallester.backend.domain.dto.CustomerDto;
import com.wallester.backend.domain.mapper.CustomerMapper;
import com.wallester.backend.exception.ApiException;
import com.wallester.backend.persist.entity.CustomerEntity;
import com.wallester.backend.persist.repository.CustomerRepository;
import com.wallester.backend.utils.PaginationUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@Slf4j
@AllArgsConstructor
public class CustomerService {
    private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);
    private final CustomerRepository repository;

    /**
     * Creates customer in DB
     *
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
        log.debug("Trying to save customer in the DB: {}", entity);
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
    @Transactional(rollbackOn = Throwable.class)
    public CustomerDto editCustomer(int id, CustomerDto dto) {
        log.debug("Trying to find entity in the DB with id = {}", id);
        Optional<CustomerEntity> optional = repository.findById(id);
        if (optional.isPresent()) {
            CustomerEntity entity = optional.get();
            CustomerEntity newEntity =  mapper.mapToEntity(dto);
            newEntity.setId(entity.getId());
            log.debug("Trying to save updated customer in the DB: {}", newEntity);
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
        log.debug("Trying to find entities in the DB with firstName = {} and lastName = {}", firstName, lastName);
        return repository.findAllByFirstNameAndLastName(firstName, lastName).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Returns all customers with matched names
     *
     * @param firstName customer's first name
     * @param lastName  customer's last name
     * @return          all matches
     */
    public CustomersResponse findByFirstNameAndLastNameWithPagination(String firstName, String lastName,
                                                                      Integer page, Integer elements,
                                                                      String sortAsc, String sortDesc) {
        log.debug("Trying to find entities in the DB with firstName = {} and lastName = {}", firstName, lastName);
        Pageable pageable = PaginationUtils.makePageable(page, elements, sortAsc, sortDesc);

        Page<CustomerEntity> resultPage =  repository.findAllByFirstNameAndLastName(firstName, lastName, pageable);
        return getCustomersResponse(resultPage);
    }

    /**
     * Returns all saved customers. This method supports paging and sorting.
     *
     * @param page      page
     * @param elements  number of elements
     * @param sortAsc   sort by (asc)
     * @param sortDesc  sort by (desc)
     * @return          all {@link CustomerEntity} on the page
     */
    public CustomersResponse findAll(Integer page, Integer elements,
                                     String sortAsc, String sortDesc) {
        Pageable pageable = PaginationUtils.makePageable(page, elements, sortAsc, sortDesc);

        Page<CustomerEntity> resultPage = repository.findAll(pageable);
        return getCustomersResponse(resultPage);
    }

    /**
     * Returns operation result (duplicated code) in methods
     * {@link #findByFirstNameAndLastNameWithPagination(String, String, Integer, Integer, String, String)}
     * and {@link #findAll(Integer, Integer, String, String)}
     *
     * @param resultPage    page from repository
     * @return              filled {@link CustomersResponse}
     */
    private CustomersResponse getCustomersResponse(Page<CustomerEntity> resultPage) {
        List<CustomerDto> resultList = resultPage.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
        if (resultList.size() > 0) {
            return new CustomersResponse(resultList, resultPage.getTotalPages(), resultPage.getTotalElements());
        }
        return new CustomersResponse(resultList, null, null);
    }
}
