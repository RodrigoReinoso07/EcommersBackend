package com.shopzone.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductDTO {

    private long id;
    
    private String nombre;

    private double precio;

    private String descripcion;

}
