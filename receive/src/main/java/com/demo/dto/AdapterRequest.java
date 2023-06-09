package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdapterRequest implements Serializable {
    private String workflowId;
    private String activityId;
    private String jsonData;
}
