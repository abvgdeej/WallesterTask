package com.wallester.backend.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallester.backend.domain.dto.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("Request body for create new customer")
public class CustomerCreateRequest {
    @JsonProperty(value = "customer", required = true)
    @ApiModelProperty("Customer")
    private CustomerDto customerDto;
}
