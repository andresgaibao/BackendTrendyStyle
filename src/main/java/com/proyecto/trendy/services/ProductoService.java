package com.proyecto.trendy.services;

import com.proyecto.trendy.entity.Producto;
import com.proyecto.trendy.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto createProducto(Producto producto /*MultipartFile imagen*/) /*throws IOException*/ {
        Producto nuevoProducto = new Producto();
        nuevoProducto.setName(producto.getName());
        nuevoProducto.setPrecio(producto.getPrecio());

        /* (imagen != null) {
            // Lógica para manejar la imagen, por ejemplo, guardarla en el sistema de archivos o base de datos
            ///nuevoProducto.setImagen("ruta/a/la/imagen"); // Actualiza con la ruta correcta
        }*/

        nuevoProducto.setStock(producto.getStock());
        nuevoProducto.setTallas(producto.getTallas());
        nuevoProducto.setMarca(producto.getMarca());

        return productoRepository.save(nuevoProducto);
    }

    public Producto updateProducto(Integer id, Producto updatedProducto/*MultipartFile updatedImagen*/) /*throws IOException*/ {
        Producto existingProducto = productoRepository.findById(id)
                .orElseThrow();

        existingProducto.setName(updatedProducto.getName());
        existingProducto.setPrecio(updatedProducto.getPrecio());

        /*if (updatedImagen != null) {
            // Lógica para manejar la imagen actualizada
            existingProducto.setImagen("ruta/a/la/imagen/actualizada"); // Actualiza con la ruta correcta
        }*/

        existingProducto.setStock(updatedProducto.getStock());
        existingProducto.setTallas(updatedProducto.getTallas());
        existingProducto.setMarca(updatedProducto.getMarca());

        return productoRepository.save(existingProducto);
    }

    public void deleteProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto getProductoById(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow();
    }
}
