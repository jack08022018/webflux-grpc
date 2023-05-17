package com.demo.entity.mssql;

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
@Table("book")
public class BookEntity implements Serializable {
    @Id
    private Integer id;

    @Column("book_type_id")
    private Integer bookTypeId;

    @Column("book_name")
    private String bookName;

}
