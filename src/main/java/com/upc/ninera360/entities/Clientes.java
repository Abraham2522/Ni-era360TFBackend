package com.upc.ninera360.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "direccion", length = 150, nullable = false)
    private String direccion;

    @Column(name = "telefono_contacto", length = 20, nullable = false)
    private String telefonoContacto;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UserProfile usuario;

    @OneToMany(mappedBy = "cliente")
    private List<Chat> chats;
}