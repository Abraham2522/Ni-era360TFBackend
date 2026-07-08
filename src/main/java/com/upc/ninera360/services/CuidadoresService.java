package com.upc.ninera360.services;

import com.upc.ninera360.dtos.CuidadoresDTO;

import java.util.List;

public interface CuidadoresService {

    CuidadoresDTO insertarCuidador(CuidadoresDTO cuidadoresDTO);
    CuidadoresDTO actualizarCuidador(CuidadoresDTO cuidadoresDTO);
    void eliminarCuidador(long id);
    List<CuidadoresDTO> listarCuidadores();
    CuidadoresDTO buscarPorId(long id);
    long count();
    List<CuidadoresDTO> findByDescripcion(String descripcion);
    CuidadoresDTO findByIdUsuario(Long idUsuario);
}