package com.huytd.productservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class BaseResponse {
    @Builder.Default
    private String message = "successfully";
    @Builder.Default
    private Integer code = 200;
    private Object data;
}
