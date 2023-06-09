package com.demo.repository.mssql.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "rental_new")
public class RentalNewEntity implements Serializable {
//    @JsonIgnore
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rental_seq")
    @SequenceGenerator(name="rental_seq", sequenceName="rental_seq", allocationSize = 50)
    @Column("rental_id")
    private Long rentalId;

    @JsonIgnore
    @Column("rental_date")
    private LocalDateTime rentalDate;
//
    @Column("inventory_id")
    private Long inventoryId;
//
//    @JsonIgnore
    @Column("customer_id")
    private Long customerId;
//
    @JsonIgnore
    @Column("return_date")
    private LocalDateTime returnDate;

//    @JsonIgnore
    @Column("staff_id")
    private Long staffId;

    @JsonIgnore
    @Column("last_update")
    private LocalDateTime lastUpdate;

//    @JsonIgnore
    @Column("status")
//    @Convert(converter = StatusConverter.class)
    private String status;

}
