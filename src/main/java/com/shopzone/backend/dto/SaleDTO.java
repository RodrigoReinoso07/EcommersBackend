package com.shopzone.backend.dto;

import java.time.LocalDate;
import java.util.List;

import com.shopzone.backend.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaleDTO {

    private long id;

    List<Product_SaleDTO> products;

    private User user;

    private double costoTotal;

    private LocalDate date;
}
