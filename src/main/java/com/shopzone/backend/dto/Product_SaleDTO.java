package com.shopzone.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Product_SaleDTO {

    private long productSaleId;

    private ProductDTO product;

    private SaleDTO sale;

    private int cantidad;

}
