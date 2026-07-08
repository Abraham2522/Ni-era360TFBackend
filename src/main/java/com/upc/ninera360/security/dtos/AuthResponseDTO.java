package com.upc.ninera360.security.dtos;

import com.upc.ninera360.dtos.ResenasDTO;
import lombok.Data;
import java.util.List;

@Data
public class AuthResponseDTO {
    private String jwt;
    private List<String> roles;
    private Long idUsuario;
    private Long idCliente;
    private Long idCuidador;
}