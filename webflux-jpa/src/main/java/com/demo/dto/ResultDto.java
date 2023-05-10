package com.demo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto<T> {
    private String responseStatus;
    private String description;
    private T data;
    private T db;
}
