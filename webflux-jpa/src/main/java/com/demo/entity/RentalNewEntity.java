package com.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "rental_new")
public class RentalNewEntity implements Serializable {
//    @JsonIgnore
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rental_seq")
    @SequenceGenerator(name="rental_seq", sequenceName="rental_seq", allocationSize = 50)
    @Column(name = "rental_id")
    private Integer rentalId;

    @JsonIgnore
    @Column(name = "rental_date")
    private LocalDateTime rentalDate;
//
    @Column(name = "inventory_id")
    private Integer inventoryId;
//
//    @JsonIgnore
    @Column(name = "customer_id")
    private Integer customerId;
//
    @JsonIgnore
    @Column(name = "return_date")
    private LocalDateTime returnDate;

//    @JsonIgnore
    @Column(name = "staff_id")
    private Integer staffId;

    @JsonIgnore
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

//    @JsonIgnore
    @Column(name = "status")
//    @Convert(converter = StatusConverter.class)
    private String status;

}
