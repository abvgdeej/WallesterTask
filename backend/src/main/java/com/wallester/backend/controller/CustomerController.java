package com.wallester.backend.controller;

import com.wallester.backend.controller.request.CustomerCreateRequest;
import com.wallester.backend.controller.request.CustomerEditRequest;
import com.wallester.backend.controller.response.CustomerEditResponse;
import com.wallester.backend.controller.response.CustomersResponse;
import com.wallester.backend.domain.dto.CustomerDto;
import com.wallester.backend.utils.ExceptionUtils;
import com.wallester.backend.exception.ResponseErrorDto;
import com.wallester.backend.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("v1/customer")
@Api(tags = {"Customers"}, consumes = "application/json", produces = "application/json")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @ApiOperation(value = "Create new object \"Customer\" in the database.",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseErrorDto.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseErrorDto.class)
    })
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CustomerCreateRequest request, BindingResult result) {
        ExceptionUtils.checkValidationErrors(result);
        service.createCustomer(request.getCustomerDto());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Edit current entity \"Customer\" with received id.",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseErrorDto.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseErrorDto.class)
    })
    @PutMapping(path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerEditResponse> editCustomer(@PathVariable("id") int id,
                                                             @RequestBody @Valid CustomerEditRequest request,
                                                             BindingResult result) {
        ExceptionUtils.checkValidationErrors(result);
        CustomerDto dto = request.getCustomerDto();
        //todo CustomerDto dtoResult =
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Returns \"Customer\" entity with received parameters.",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseErrorDto.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseErrorDto.class)
    })
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomersResponse> findCustomersByNames(
            @RequestParam(value = "firstName")
            @Valid
            @NotBlank
            @Size(max = 100, message = "First name must be less than 100 characters") String firstName,
            @RequestParam(value = "lastName")
            @Valid
            @NotBlank
            @Size(max = 100, message = "Last name must be less than 100 characters") String lastName,
            BindingResult result) {
        ExceptionUtils.checkValidationErrors(result);

        List<CustomerDto> dtoList = service.findByFirstNameAndLastName(firstName, lastName);
        if (CollectionUtils.isEmpty(dtoList)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        CustomersResponse response = new CustomersResponse(dtoList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns all saved \"Customer\" entities.",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseErrorDto.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseErrorDto.class)
    })
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
