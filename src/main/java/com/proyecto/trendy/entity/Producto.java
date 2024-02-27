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
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Double precio;
    /*private String imagen; */ // Ruta o enlace a la imagen del producto
    private Integer stock;      // Cantidad en stock
    private String tallas;  // Tallas disponibles

    @ManyToOne
    private Marca marca;
}
