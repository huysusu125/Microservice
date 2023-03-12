package com.huytd.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    @Builder.Default
    private String message = "successfully";
    @Builder.Default
    private Integer code = 200;
    private Object data;
}
