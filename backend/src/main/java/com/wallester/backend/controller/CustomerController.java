package com.wallester.backend.controller;

import com.wallester.backend.controller.request.CustomerCreateRequest;
import com.wallester.backend.controller.request.CustomerEditRequest;
import com.wallester.backend.controller.response.CustomerEditResponse;
import com.wallester.backend.controller.response.CustomersResponse;
import com.wallester.backend.domain.dto.CustomerDto;
import com.wallester.backend.exception.ResponseErrorDto;
import com.wallester.backend.persist.entity.CustomerEntityTables;
import com.wallester.backend.service.CustomerService;
import com.wallester.backend.utils.ExceptionUtils;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("v1/customer")
@AllArgsConstructor
@Api(tags = {"Customers"}, consumes = "application/json", produces = "application/json")
public class CustomerController {
    private final CustomerService service;

    @ApiOperation(value = "Create new object \"Customer\" in the database.",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseErrorDto.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseErrorDto.class)
    })
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCustomer(
            @RequestBody @Valid
            @ApiParam(name = "request", required = true, value = "Customer Create Request")
            CustomerCreateRequest request, BindingResult result) {
        log.debug("Received request body for create customer: {}", request);
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
    public ResponseEntity<CustomerEditResponse> editCustomer(
            @PathVariable("id")
            @ApiParam(name = "id", required = true, value = "Customer id in DB")
            int id,
            @ApiParam(name = "request", required = true, value = "Customer Edit Request")
            @RequestBody @Valid CustomerEditRequest request,
            BindingResult result) {
        log.debug("Received request body for edit customer: {}, id = {}", request, id);
        ExceptionUtils.checkValidationErrors(result);
        CustomerDto dto = request.getCustomerDto();
        CustomerDto dtoResult = service.editCustomer(id, dto);
        return new ResponseEntity<>(new CustomerEditResponse(dtoResult), HttpStatus.OK);
    }

    @ApiOperation(value = "Returns \"Customer\" entity with received parameters. Pagination is not supported.",
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
            @ApiParam(name = "firstName", required = true, value = "Customer's first name")
            @Size(max = 100, message = "First name must be less than 100 characters")
            String firstName,
            @RequestParam(value = "lastName")
            @Valid
            @NotBlank
            @ApiParam(name = "lastName", required = true, value = "Customer's last name")
            @Size(max = 100, message = "Last name must be less than 100 characters")
            String lastName) {
        List<CustomerDto> dtoList = service.findByFirstNameAndLastName(firstName, lastName);
        if (CollectionUtils.isEmpty(dtoList)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomersResponse response = new CustomersResponse(dtoList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns \"Customer\" entity with received parameters. Pagination supported.",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseErrorDto.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseErrorDto.class)
    })
    @GetMapping(path = "/firstName={firstName},lastName={lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomersResponse> findCustomersByNamesWithPagination(
            @PathVariable(value = "firstName")
            @Valid
            @NotBlank
            @ApiParam(name = "firstName", required = true, value = "Customer's first name")
            @Size(max = 100, message = "First name must be less than 100 characters")
            String firstName,
            @PathVariable(value = "lastName")
            @Valid
            @NotBlank
            @ApiParam(name = "lastName", required = true, value = "Customer's last name")
            @Size(max = 100, message = "Last name must be less than 100 characters")
            String lastName,
            @RequestParam(value = "page", required = false)
            @ApiParam(name = "Page", value = "Number of page. Numbering starts from 0")
            Integer page,
            @RequestParam(value = "elements", required = false)
            @ApiParam(name = "Elements", value = "Elements on the page. 5 by default.")
            Integer elements,
            @RequestParam(value = "sortAsc", required = false)
            @ApiParam(name = "SortAsc", value = "Sort by (ASC)")
            CustomerEntityTables sortAsc,
            @RequestParam(value = "sortDesc", required = false)
            @ApiParam(name = "SortDesc", value = "Sort by (DESC)")
            CustomerEntityTables sortDesc) {
        String sortAscStr = null, SortDescStr = null;
        if (sortAsc != null) {
            sortAscStr = sortAsc.getValue();
        }
        if (sortDesc != null) {
            SortDescStr = sortDesc.getValue();
        }

        CustomersResponse result = service.findByFirstNameAndLastNameWithPagination(
                firstName, lastName,
                page, elements, sortAscStr, SortDescStr);
        if (CollectionUtils.isEmpty(result.getCustomerDtoList())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns all saved \"Customer\" entities. This endpoint always returns first page with 5 elements.",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseErrorDto.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseErrorDto.class)
    })
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomersResponse> findAllCustomers() {
        return findAllCustomersCommon(0, 5, null, null);
    }

    @ApiOperation(value = "Returns all saved \"Customer\" entities. " +
            "This endpoint has paging and sorting request params",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ResponseErrorDto.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ResponseErrorDto.class)
    })
    @GetMapping(path = "/all-paged", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomersResponse> findAllCustomersPageAndSorting(
            @RequestParam(value = "page", required = false)
            @ApiParam(name = "Page", value = "Number of page. Numbering starts from 0")
            Integer page,
            @RequestParam(value = "elements", required = false)
            @ApiParam(name = "Elements", value = "Elements on the page. 5 by default.")
            Integer elements,
            @RequestParam(value = "sortAsc", required = false)
            @ApiParam(name = "SortAsc", value = "Sort by (ASC)")
            CustomerEntityTables sortAsc,
            @RequestParam(value = "sortDesc", required = false)
            @ApiParam(name = "SortDesc", value = "Sort by (DESC)")
            CustomerEntityTables sortDesc) {
        return findAllCustomersCommon(page, elements, sortAsc, sortDesc);
    }

    private ResponseEntity<CustomersResponse> findAllCustomersCommon(Integer page, Integer elements,
                                                                     CustomerEntityTables sortAsc,
                                                                     CustomerEntityTables sortDesc) {
        String sortAscStr = null, SortDescStr = null;
        if (sortAsc != null) {
            sortAscStr = sortAsc.getValue();
        }
        if (sortDesc != null) {
            SortDescStr = sortDesc.getValue();
        }

        CustomersResponse result = service.findAll(page, elements, sortAscStr, SortDescStr);
        if (CollectionUtils.isEmpty(result.getCustomerDtoList())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
