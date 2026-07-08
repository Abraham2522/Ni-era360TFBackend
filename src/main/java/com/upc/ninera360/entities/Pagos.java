package com.upc.ninera360.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="pagos")
public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pago")
    private Long idPago;

    @Column(name = "monto_pago", nullable = false)
    private BigDecimal montoPago;

    @Column(name = "estado_pago", nullable = false)
    private Boolean estadoPago;

    @Column(name = "pagada_en", nullable = false)
    private LocalDateTime pagadaEn;

    @Column(name = "metodo_pago", length = 50, nullable = false)
    private String metodoPago;

    @ManyToOne
    @JoinColumn(name="id_reserva", nullable = false)
    private Reservas reservas;
}
