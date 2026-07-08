package com.upc.ninera360.security.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroClienteRequestDTO {
    private String username;
    private String password;

    private String nombre;
    private String apellidos;
    private String dni;
    private String direccion;
    private String telefono;
    private String correo;

    private String descripcion;
    private String telefonoContacto;
}