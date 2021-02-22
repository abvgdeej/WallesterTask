package com.wallester.backend.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallester.backend.domain.dto.CustomerDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor(onConstructor_=@JsonCreator)
@Data
@ApiModel("Edit customer request")
public class CustomerEditRequest {
    @JsonProperty(value = "customer", required = true)
    @ApiModelProperty("Customer")
    @NotNull
    @Valid
    private CustomerDto customerDto;
}
