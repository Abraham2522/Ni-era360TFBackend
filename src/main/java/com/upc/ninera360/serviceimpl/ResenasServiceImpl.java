package com.upc.ninera360.serviceimpl;

import com.upc.ninera360.dtos.ResenasDTO;
import com.upc.ninera360.entities.Resenas;
import com.upc.ninera360.entities.Reservas;
import com.upc.ninera360.repositories.ResenasRepositorio;
import com.upc.ninera360.services.ResenasService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.List;

@Service
public class ResenasServiceImpl implements ResenasService {
    @Autowired
    private ResenasRepositorio resenasRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public ResenasDTO insertarResena(ResenasDTO resenasDTO) {
        if (resenasDTO.getIdResena() != null &&
                resenasRepositorio.existsById(resenasDTO.getIdResena())) {
            throw new RuntimeException(
                    "La reseña con ID " + resenasDTO.getIdResena() + " ya existe.");
        }

        if (resenasDTO.getActivo() == null) {
            resenasDTO.setActivo(true);
        }

        Resenas resenas = modelMapper.map(resenasDTO, Resenas.class);
        resenas.setCreadoEn(LocalDateTime.now(ZoneId.of("America/Lima")));

        if (resenasDTO.getIdReserva() != null) {
            Reservas reserva = new Reservas();
            reserva.setIdReserva(resenasDTO.getIdReserva());
            resenas.setReservas(reserva);
        }

        resenas = resenasRepositorio.save(resenas);

        return modelMapper.map(resenas, ResenasDTO.class);
    }

    @Transactional
    @Override
    public ResenasDTO actualizarResena(ResenasDTO resenasDTO) {

        return resenasRepositorio.findById(resenasDTO.getIdResena())
                .map(existing -> {

                    if (resenasDTO.getCalificacion() != null) {
                        existing.setCalificacion(resenasDTO.getCalificacion());
                    }

                    if (resenasDTO.getComentario() != null) {
                        existing.setComentario(resenasDTO.getComentario());
                    }

                    if (resenasDTO.getActivo() != null) {
                        existing.setActivo(resenasDTO.getActivo());
                    }

                    Resenas actualizado = resenasRepositorio.save(existing);
                    return modelMapper.map(actualizado, ResenasDTO.class);
                })
                .orElseThrow(() -> new RuntimeException(
                        "Reseña con ID " + resenasDTO.getIdResena() + " no encontrada"
                ));
    }

    @Transactional
    @Override
    public void eliminarResena(long id) {
        if (!resenasRepositorio.existsById(id)) {
            throw new RuntimeException(
                    "Reseña no encontrada con ID " + id);
        }
        resenasRepositorio.deleteById(id);
    }

    @Override
    public List<ResenasDTO> listarResenas() {
        return resenasRepositorio.findAll()
                .stream()
                .map(resenas -> {
                    ResenasDTO dto = modelMapper.map(resenas, ResenasDTO.class);

                    if (resenas.getReservas() != null) {
                        dto.setIdReserva(resenas.getReservas().getIdReserva());
                    }

                    return dto;
                })
                .toList();
    }

    @Override
    public ResenasDTO buscarPorId(long id) {
        return resenasRepositorio.findById(id)
                .map(resenas -> {
                    ResenasDTO dto = modelMapper.map(resenas, ResenasDTO.class);

                    if (resenas.getReservas() != null) {
                        dto.setIdReserva(resenas.getReservas().getIdReserva());
                    }

                    return dto;
                })
                .orElseThrow(() -> new RuntimeException(
                        "Reseña no encontrada con ID: " + id
                ));
    }
    @Override
    public long count() {
        long count = resenasRepositorio.count();
        if (count == 0) {
            throw new RuntimeException("No existen reseñas registradas");
        }
        return count;
    }

    @Override
    public List<ResenasDTO> findByCalificacion(String calificacion) {
        List<Resenas> lista =
                resenasRepositorio.findByCalificacion(calificacion);

        if (lista.isEmpty()) {
            throw new RuntimeException(
                    "No existen reseñas con esa calificación");
        }

        return lista.stream()
                .map(r -> modelMapper.map(r, ResenasDTO.class))
                .toList();
    }
}