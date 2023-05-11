package com.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "rental")
public class RentalEntity implements Serializable {
//    @JsonIgnore
    @Id
    @Column("rental_id")
    private Long rentalId;

//    @JsonIgnore
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
//    @Column("status")
//    @Convert(converter = StatusConverter.class)
//    private String status;

}
