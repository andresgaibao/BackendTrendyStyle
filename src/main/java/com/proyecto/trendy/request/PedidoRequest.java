package com.proyecto.trendy.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {
    private String name;
    private String address;
    private String departamento;
    private String city;
    private String barrio;
    private String email;
    private String num_cel;
    private Integer product_id;
    private Integer cantidad;




}
