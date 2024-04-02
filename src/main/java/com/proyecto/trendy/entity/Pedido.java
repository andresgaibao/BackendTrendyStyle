package com.proyecto.trendy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int num_pedido;
    private Date fecha;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany
    @JoinColumn(name = "items_pedido")
    private List<DetallePedido> productos = new ArrayList<> ();

    private BigDecimal total;


}
