package com.wallester.backend.controller;

import com.wallester.backend.controller.request.CustomerCreateRequest;
import com.wallester.backend.controller.request.CustomerEditRequest;
import com.wallester.backend.controller.response.CustomersResponse;
import com.wallester.backend.domain.dto.CustomerDto;
import com.wallester.backend.exception.ApiException;
import com.wallester.backend.domain.enums.Gender;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(value = {"/scripts/create-customers-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/scripts/create-customers-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Slf4j
public class CustomerControllerTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Autowired
    private CustomerController controller;

    @Test
    public void whenFindAll_thenListSizeIsFour() {
        //arrange
        int expected = 4;

        //act
        CustomersResponse response = controller.findAllCustomers().getBody();

        //assert
        assertNotNull(response);
        assertEquals("List of DTO size must be four: ", expected, response.getCustomerDtoList().size());
    }

    @Test
    public void whenFindByNames_thenResultEqualsStub() throws ParseException {
        //arrange
        String firstName = "Aleksandr";
        String lastName = "Aleksandrov";
        CustomerDto customerDto = createCustomerStub("dd.MM.yyyy", "01.01.1992");
        customerDto.setId(4);

        //act
        CustomersResponse response = controller.findCustomersByNames(firstName, lastName).getBody();

        //assert
        assertNotNull(response);
        CustomerDto newDto = response.getCustomerDtoList().get(0);
        log.info("Old dto: {}, new dto: {}", customerDto, newDto);
        assertEquals("DTO must be equals: ", customerDto, newDto);
    }

    @Test
    public void whenPut_thenNewDtoEqualsStub() throws ParseException {
        //arrange
        CustomerDto customerDto = createCustomerStub("dd.MM.yyyy", "01.01.1990");
        customerDto.setId(4);
        customerDto.setFirstName("Ivan");
        customerDto.setLastName("Ivanov");

        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        //act
        CustomerDto newDto = Objects.requireNonNull(controller.editCustomer(4, new CustomerEditRequest(customerDto), result)
                .getBody())
                .getCustomerDto();

        //assert
        log.info("Old dto: {}, new dto: {}", customerDto, newDto);
        assertEquals("DTO must be equals: ", customerDto, newDto);
    }

    @Test
    public void whenEmailExists_thenThrowDataIntegrityViolationException() throws ParseException {
        //arrange
        CustomerDto customerDto = createCustomerStub("dd.MM.yyyy", "01.01.1990");

        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        //act
        exceptionRule.expect(DataIntegrityViolationException.class);
        exceptionRule.expectMessage("could not execute statement; SQL [n/a]; constraint [customers_email_key]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement");
        controller.createCustomer(new CustomerCreateRequest(customerDto), result);
    }

    @Test
    public void whenDateWrong_thenThrowApiException() throws ParseException {
        //arrange
        CustomerDto customerDto = createCustomerStub("dd.MM", "01.01");

        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        //act
        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Validation error(s): ");
        controller.createCustomer(new CustomerCreateRequest(customerDto), result);
    }

    private CustomerDto createCustomerStub(String pattern, String sourceDate) throws ParseException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Aleksandr");
        customerDto.setLastName("Aleksandrov");
        java.util.Date date = new SimpleDateFormat(pattern).parse(sourceDate);
        customerDto.setBirthDate(new Date(date.getTime()));
        customerDto.setGender(Gender.MALE);
        customerDto.setEmail("Aleksandr.Aleksandrov@gmail.com");
        return customerDto;
    }
}