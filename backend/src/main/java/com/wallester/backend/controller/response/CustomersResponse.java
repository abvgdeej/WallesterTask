package com.wallester.backend.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallester.backend.domain.dto.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@ApiModel("Response body witch contains list of customers")
public class CustomersResponse {
    @JsonProperty(required = true)
    @ApiModelProperty("List of customers")
    private List<CustomerDto> customerDtoList;
}
