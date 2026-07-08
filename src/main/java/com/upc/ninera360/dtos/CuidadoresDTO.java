package com.upc.ninera360.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CuidadoresDTO {
    private Long idUsuario;
    private Long idCuidador;
    private String nombre;
    private String apellidos;
    private String descripcion;
    private BigDecimal tarifa;
    private String experiencia;
    private String antecedentes;
    private String disponibilidad;
    private Double calificacion;
    private Boolean activo;
}