package com.wallester.backend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallester.backend.utils.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("Customer")
public class CustomerDto {
    @JsonProperty(value = "first_name", required = true)
    @JsonInclude
    @NotBlank(message = "First name cannot be blank")
    @Size(max = 100, message = "First name must be less than 100 characters")
    @ApiModelProperty(value = "First name", required = true, position = 1, example = "Ivan")
    private String firstName;

    @JsonProperty(value = "last_name", required = true)
    @JsonInclude
    @Size(max = 100, message = "First name must be less than 100 characters")
    @NotBlank(message = "Last name cannot be blank")
    @ApiModelProperty(value = "Last name", required = true, position = 2, example = "Ivanov")
    private String lastName;

    @JsonProperty(value = "birth_date", required = true)
    @JsonInclude
    @NotNull(message = "Birth date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @ApiModelProperty(value = "Birth date", required = true, position = 3, example = "01.01.1990")
    private Calendar birthDate;

    @JsonProperty(value = "gender", required = true)
    @JsonInclude
    @NotBlank(message = "Gender may be Male or Female")
    @ApiModelProperty(value = "Gender", required = true, position = 4, example = "Male")
    private Gender gender;

    @JsonProperty(value = "email", required = true)
    @JsonInclude
    @Email(message = "Email should be valid")
    @ApiModelProperty(value = "Email", required = true, position = 5, example = "ivan.ivanov@gmail.com")
    private String email;

    @JsonProperty(value = "address")
    @Size(max = 200, message = "First name must be less than 200 characters")
    @ApiModelProperty(value = "Address", position = 6, example = "Moscow, Tverskaya st., 1-1")
    private String address;
}
