package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto<T> {
    @JsonIgnore
    private String lmid;

    private String requestId;
    private String requestDateTime;
    private T data;
}
