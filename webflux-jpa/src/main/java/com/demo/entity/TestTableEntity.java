package com.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "test_table")
public class TestTableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rental_seq")
    @SequenceGenerator(name="rental_seq", sequenceName="rental_seq", allocationSize = 50)
    @Column(name = "rental_id")
    private Integer rentalId;

    @Column(name = "message")
    private String message;

}
