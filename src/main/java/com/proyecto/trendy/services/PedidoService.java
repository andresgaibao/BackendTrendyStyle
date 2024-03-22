package com.proyecto.trendy.services;

import com.proyecto.trendy.entity.Marca;
import com.proyecto.trendy.entity.Pedidos;
import com.proyecto.trendy.entity.Product;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.repository.PedidoRepository;
import com.proyecto.trendy.repository.ProductRepository;
import com.proyecto.trendy.request.PedidoRequest;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ProductRepository prorepo;

    public PedidoService(PedidoRepository repository, ProductRepository prorepo) {
        this.repository = repository;
        this.prorepo = prorepo;
    }


    public Pedidos createPedido(String name, String address, String departamento, String city,
                                String barrio, String email, String numCel, Integer cantidad, Product id) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del pedido no puede ser nulo o vacío");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección del pedido no puede ser nula o vacía");
        }
        if (departamento == null || departamento.trim().isEmpty()) {
            throw new IllegalArgumentException("El departamento del pedido no puede ser nulo o vacío");
        }
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("La ciudad del pedido no puede ser nula o vacía");
        }
        if (barrio == null || barrio.trim().isEmpty()) {
            throw new IllegalArgumentException("El barrio del pedido no puede ser nulo o vacío");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email del pedido no puede ser nulo o vacío");
        }
        if (numCel == null || numCel.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de celular del pedido no puede ser nulo o vacío");
        }
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad del pedido debe ser mayor que cero");
        }
        if (id == null) {
            throw new IllegalArgumentException("El producto del pedido no puede ser nulo");
        }

        Pedidos pedido = new Pedidos();
        pedido.setName(name);
        pedido.setAddress(address);
        pedido.setDepartamento(departamento);
        pedido.setCity(city);
        pedido.setBarrio(barrio);
        pedido.setEmail(email);
        pedido.setNum_cel (numCel);
        pedido.setCantidad(cantidad);
        pedido.setProduct(id);

        return repository.save(pedido);
    }


}
