package com.upc.ninera360.dtos;

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
public class PagosDTO {
    private Long idPago;
    private Long idReserva;
    private BigDecimal montoPago;
    private Boolean estadoPago;
    private LocalDateTime pagadaEn;
    private String metodoPago;
}
