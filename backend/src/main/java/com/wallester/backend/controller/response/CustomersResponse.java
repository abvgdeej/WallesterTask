package com.wallester.backend.controller.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallester.backend.domain.dto.CustomerDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor(onConstructor_=@JsonCreator)
@Data
@ApiModel("Response witch contains list of customers")
public class CustomersResponse {
    @JsonProperty(value = "customer_list", required = true)
    @ApiModelProperty("List of customers")
    private List<CustomerDto> customerDtoList;
    @JsonProperty(value = "total_pages")
    @ApiModelProperty("Total pages")
    private Integer totalPages;
    @JsonProperty(value = "total_elements")
    @ApiModelProperty("Total elements")
    private Long totalElements;

    public CustomersResponse(List<CustomerDto> customerDtoList) {
        this.customerDtoList = customerDtoList;
    }
}
