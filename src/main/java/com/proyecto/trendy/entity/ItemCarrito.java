package com.proyecto.trendy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items_carrito")
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Carrito carrito;

    @ManyToOne
    private Producto producto;

    private int cantidad;

    // Otros atributos y métodos que consideres necesarios
}
