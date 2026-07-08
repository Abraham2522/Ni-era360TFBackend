package com.upc.ninera360.repositories;

import com.upc.ninera360.entities.Chat;
import com.upc.ninera360.entities.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientesRepositorio extends JpaRepository<Clientes, Long> {
    public long count();
    public List<Clientes> findByDescripcion(String descripcion);
    Optional<Clientes> findByUsuario_IdUsuario(Long idUsuario);
}
