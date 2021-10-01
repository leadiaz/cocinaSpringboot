package com.finneg.springboot.model;

import lombok.Data;

@Data
public class Cocina {
    private Integer id;
    private String nombre;
    private String codigo;
    private String tipoHeladera;
    private String tipoCocina;
    private String tipoMesada;
}
