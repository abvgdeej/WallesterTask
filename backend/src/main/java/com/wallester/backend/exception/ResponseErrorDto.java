package com.wallester.backend.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@ApiModel("Error")
public class ResponseErrorDto {
    @ApiModelProperty(value = "Error status", position = 1, example = "500")
    private Integer status;
    @ApiModelProperty(value = "Error timestamp", position = 2, example = "2019-11-14T09:11:39.342+0000")
    private OffsetDateTime time;
    @ApiModelProperty(value = "Error message", position = 3, example = "Wrong Person id")
    private String message;
}
