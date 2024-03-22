package com.proyecto.trendy.controller;

import com.proyecto.trendy.entity.Pedidos;
import com.proyecto.trendy.entity.Product;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.request.PedidoRequest;
import com.proyecto.trendy.services.PedidoService;
import com.proyecto.trendy.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {
    private final PedidoService service;
    private final ProductoService productService;
    public PedidoController(PedidoService service, ProductoService productService) {
        this.service = service;
        this.productService = productService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Pedidos> crearPedido(@RequestBody PedidoRequest pedidoRequest) {
        Product product = productService.getProductById(pedidoRequest.getProduct_id ());
        Pedidos pedido = service.createPedido(pedidoRequest.getName(), pedidoRequest.getAddress(),
                pedidoRequest.getDepartamento(), pedidoRequest.getCity(), pedidoRequest.getBarrio(),
                pedidoRequest.getEmail(), pedidoRequest.getNum_cel (), pedidoRequest.getCantidad (), product);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }


}
