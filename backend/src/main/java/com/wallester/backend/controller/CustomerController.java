package com.wallester.backend.controller;

import com.wallester.backend.controller.request.CustomerCreateRequest;
import com.wallester.backend.controller.request.CustomerEditRequest;
import com.wallester.backend.controller.response.CustomerEditResponse;
import com.wallester.backend.controller.response.CustomersResponse;
import com.wallester.backend.domain.dto.CustomerDto;
import com.wallester.backend.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("v1/customer")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCustomer(@RequestBody CustomerCreateRequest request) {
        CustomerDto dto = request.getCustomerDto();
        if (request == null) {
            //todo throw new ServiceException
        }
        service.createCustomer(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerEditResponse> editCustomer(@PathVariable("id") int id,
                                                             @RequestBody CustomerEditRequest request) {
        CustomerDto dto = request.getCustomerDto();
        if (dto == null) {
            //todo return new ServiceException
        }
        //todo CustomerDto dtoResult =
        return new ResponseEntity<>(new CustomerEditResponse(), HttpStatus.OK);
    }

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomersResponse> findCustomersByNames(
            @RequestParam(value = "firstName")
            @NotBlank
            @Size(max = 100, message = "First name must be less than 100 characters") String firstName,
            @RequestParam(value = "lastName")
            @NotBlank
            @Size(max = 100, message = "Last name must be less than 100 characters") String lastName) {
        List<CustomerDto> dtoList = service.findByFirstNameAndLastName(firstName, lastName);
        if (CollectionUtils.isEmpty(dtoList)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomersResponse response = new CustomersResponse(dtoList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomersResponse> findAllCustomers() {
        List<CustomerDto> dtoList = service.findAll();
        if (CollectionUtils.isEmpty(dtoList)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomersResponse response = new CustomersResponse(dtoList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
