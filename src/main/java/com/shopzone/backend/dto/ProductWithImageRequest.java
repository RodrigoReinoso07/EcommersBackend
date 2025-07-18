package com.shopzone.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductWithImageRequest {

    private String nombre;
    private double precio;
    private int stock;
    private String categoria;
    private String descripcion;
    private String imageData;
    private String contentType;
}
