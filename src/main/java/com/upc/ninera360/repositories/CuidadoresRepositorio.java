package com.upc.ninera360.repositories;

import com.upc.ninera360.entities.Cuidadores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuidadoresRepositorio extends JpaRepository<Cuidadores, Long> {

    List<Cuidadores> findByDescripcion(String descripcion);
    Optional<Cuidadores> findByUsuario_IdUsuario(Long idUsuario);
}