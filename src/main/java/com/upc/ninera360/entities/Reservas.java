package com.upc.ninera360.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservas")
public class Reservas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes cliente;

    @ManyToOne
    @JoinColumn(name = "id_cuidador", nullable = false)
    private Cuidadores cuidador;

    @Column(name = "hora_inicio", nullable = false)
    private OffsetDateTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private OffsetDateTime horaFin;

    @Column(name = "estado", length = 30, nullable = false)
    private String estado;

    @Column(name = "direccion_servicio", length = 150, nullable = false)
    private String direccionServicio;

    @Column(name = "activo", nullable = false)
    private Boolean activo;
}
