package com.finneg.springboot.model.dto;

import lombok.Data;

@Data
public class BanioDto {
    private Long id;
    private String nombre;
    private String codigo;
    private String tipoBaniera;
    private String bidet;
    private String inodoro;
}
