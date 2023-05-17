package com.demo.entity.oracle;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table("employee")
public class EmployeeEntity implements Serializable {
    @Id
    private Long id;

    @Column("client_id")
    private Long clientId;

    @Column("employee_name")
    private String employeeName;

}
