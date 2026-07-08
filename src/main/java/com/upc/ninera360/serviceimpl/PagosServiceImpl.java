package com.upc.ninera360.serviceimpl;

import com.upc.ninera360.dtos.PagosDTO;
import com.upc.ninera360.entities.Pagos;
import com.upc.ninera360.entities.Reservas;
import com.upc.ninera360.repositories.PagosRepositorio;
import com.upc.ninera360.repositories.ReservasRepositorio;
import com.upc.ninera360.services.PagosService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class PagosServiceImpl implements PagosService {

    @Autowired
    private PagosRepositorio pagosRepositorio;

    @Autowired
    private ReservasRepositorio reservasRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    private PagosDTO convertirADTO(Pagos pago) {
        PagosDTO dto = modelMapper.map(pago, PagosDTO.class);

        if (pago.getReservas() != null) {
            dto.setIdReserva(pago.getReservas().getIdReserva());
        }

        return dto;
    }

    @Transactional
    @Override
    public PagosDTO insertarPago(PagosDTO pagosDTO) {

        if (pagosDTO.getIdPago() != null &&
                pagosRepositorio.existsById(pagosDTO.getIdPago())) {
            throw new RuntimeException("El Pago con ID " + pagosDTO.getIdPago() + " ya existe.");
        }

        if (pagosDTO.getEstadoPago() == null) {
            pagosDTO.setEstadoPago(false);
        }

        Reservas reserva = reservasRepositorio.findById(pagosDTO.getIdReserva())
                .orElseThrow(() -> new RuntimeException(
                        "Reserva no encontrada con ID: " + pagosDTO.getIdReserva()
                ));

        Pagos pagos = modelMapper.map(pagosDTO, Pagos.class);
        pagos.setReservas(reserva);
        pagos.setPagadaEn(LocalDateTime.now(ZoneId.of("America/Lima")));

        Pagos guardado = pagosRepositorio.save(pagos);

        return convertirADTO(guardado);
    }

    @Transactional
    @Override
    public PagosDTO actualizarPago(PagosDTO pagosDTO) {

        return pagosRepositorio.findById(pagosDTO.getIdPago())
                .map(existing -> {

                    if (pagosDTO.getMontoPago() != null) {
                        existing.setMontoPago(pagosDTO.getMontoPago());
                    }

                    if (pagosDTO.getMetodoPago() != null) {
                        existing.setMetodoPago(pagosDTO.getMetodoPago());
                    }

                    if (pagosDTO.getEstadoPago() != null) {
                        existing.setEstadoPago(pagosDTO.getEstadoPago());
                    }

                    if (pagosDTO.getIdReserva() != null) {
                        Reservas reserva = reservasRepositorio.findById(pagosDTO.getIdReserva())
                                .orElseThrow(() -> new RuntimeException(
                                        "Reserva no encontrada con ID: " + pagosDTO.getIdReserva()
                                ));
                        existing.setReservas(reserva);
                    }

                    Pagos actualizado = pagosRepositorio.save(existing);

                    return convertirADTO(actualizado);
                })
                .orElseThrow(() -> new RuntimeException(
                        "Pago con ID " + pagosDTO.getIdPago() + " no encontrado"
                ));
    }

    @Transactional
    @Override
    public void eliminarPago(long id) {

        if (!pagosRepositorio.existsById(id)) {
            throw new RuntimeException("Pago no encontrado con ID " + id);
        }

        pagosRepositorio.deleteById(id);
    }

    @Override
    public List<PagosDTO> listarPagos() {

        return pagosRepositorio.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Override
    public PagosDTO buscarPorId(long id) {

        return pagosRepositorio.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException(
                        "Pago no encontrado con ID: " + id
                ));
    }

    @Override
    public long count() {

        long count = pagosRepositorio.count();

        if (count == 0) {
            throw new RuntimeException("No existen pagos registrados");
        }

        return count;
    }

    @Override
    public List<PagosDTO> findByEstadoPago(Boolean estadoPago) {

        List<Pagos> lista = pagosRepositorio.findByEstadoPago(estadoPago);

        if (lista.isEmpty()) {
            throw new RuntimeException("No existen pagos con ese estado");
        }

        return lista.stream()
                .map(this::convertirADTO)
                .toList();
    }
}