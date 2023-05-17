package com.demo.entity.mariadb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "test_table_new")
public class TestTableNewEntity implements Serializable {
    @Id
    @Column("rental_id")
    private Long rentalId;

    @Column("message")
    private String message;

    @Version
    @JsonIgnore
    @Column("version")
    private Integer version;

}
