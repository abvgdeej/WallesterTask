package com.wallester.backend.controller.response;

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
@ApiModel("Response body with edit customer operation result")
public class CustomerEditResponse {
    @JsonProperty(required = true)
    @ApiModelProperty("Customer")
    private CustomerDto customerDto;
}
