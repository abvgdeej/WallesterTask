package com.wallester.backend.controller.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallester.backend.domain.dto.CustomerDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(onConstructor_=@JsonCreator)
@Data
@ApiModel("Edit customer operation result")
public class CustomerEditResponse {
    @JsonProperty(value = "customer", required = true)
    @ApiModelProperty("Customer")
    private CustomerDto customerDto;
}
