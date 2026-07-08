package com.upc.ninera360.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cuidadores")
public class Cuidadores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuidador")
    private Long idCuidador;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "tarifa", nullable = false)
    private BigDecimal tarifa;

    @Column(name = "experiencia", length = 300, nullable = false)
    private String experiencia;

    @Column(name = "antecedentes", length = 300, nullable = false)
    private String antecedentes;

    @Column(name = "disponibilidad", length = 100, nullable = false)
    private String disponibilidad;

    @Column(name = "calificacion", nullable = false)
    private Double calificacion;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UserProfile usuario;

    @OneToMany(mappedBy = "cuidador")
    private List<Chat> chats;
}
