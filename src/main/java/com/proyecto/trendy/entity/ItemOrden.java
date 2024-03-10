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
@Table(name = "items_venta")
public class ItemOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Orden orden;

    @ManyToOne
    private Producto producto;

    private int cantidad;

}
