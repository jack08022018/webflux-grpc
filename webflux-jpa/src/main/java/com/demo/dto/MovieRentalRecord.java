package com.demo.dto;

import java.time.LocalDateTime;

public record MovieRentalRecord (
    String title,
    LocalDateTime rentalDate
){}
