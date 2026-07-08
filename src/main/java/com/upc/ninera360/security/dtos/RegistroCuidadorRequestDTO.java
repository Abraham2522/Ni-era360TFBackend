package com.upc.ninera360.security.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RegistroCuidadorRequestDTO {
    private String username;
    private String password;

    private String nombre;
    private String apellidos;
    private String dni;
    private String direccion;
    private String telefono;
    private String correo;

    private String descripcion;
    private BigDecimal tarifa;
    private String experiencia;
    private String antecedentes;
    private String disponibilidad;
}