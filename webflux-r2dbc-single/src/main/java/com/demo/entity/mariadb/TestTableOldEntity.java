package com.demo.entity.mariadb;

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
@Table(name = "test_table_old")
public class TestTableOldEntity implements Serializable {
    @Id
    private Long id;

    @Column("status")
    private String status;

//    @Column("fake")
//    private String fake;

}
