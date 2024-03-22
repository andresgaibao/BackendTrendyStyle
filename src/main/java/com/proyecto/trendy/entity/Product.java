package com.proyecto.trendy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private BigDecimal price;

    @Lob
    @Column(name = "img", length = 500)
    private byte[] img;

    @ManyToOne
    private Marca marca;

    private String detail;

    @ManyToOne
    private Category category;

    public Product orElse(Object o) {
        return null;

    }
}

