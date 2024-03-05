package com.proyecto.trendy.services;

import com.proyecto.trendy.entity.Marca;
import com.proyecto.trendy.entity.Producto;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.repository.MarcaRepository;
import com.proyecto.trendy.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final MarcaRepository marcaRepository;

    public void registrarProducto(MultipartFile archivo, String name, Double precio, Integer marcaId)
            throws MyException {
        // Validar que los campos no estén vacíos
        if (name == null || name.trim().isEmpty() || precio == null || marcaId == null) {
            throw new MyException("Todos los campos son obligatorios");
        }

        // Verificar el formato de la imagen
        if (!isValidImageFormat(archivo)) {
            throw new MyException("Formato de imagen no válido. Solo se admiten archivos JPG, JPEG y PNG.");
        }

        try {
            Optional<Marca> optionalMarca = marcaRepository.findById(marcaId);

            if (optionalMarca.isPresent()) {
                Marca marca = optionalMarca.get();

                Producto producto = new Producto();
                producto.setName(name);
                producto.setPrice(precio);
                producto.setImg(archivo.getBytes());
                producto.setMarca(marca);

                productoRepository.save(producto);
            } else {
                throw new MyException("Marca no encontrada con el ID: " + marcaId);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new MyException("Error al guardar el producto");
        }
    }

    public void actualizarProducto(Integer id, MultipartFile archivo, String name, Double precio, Integer marcaId)
            throws MyException {
        // Validar que los campos no estén vacíos
        if (name == null || name.trim().isEmpty() || precio == null || marcaId == null) {
            throw new MyException("Todos los campos son obligatorios");
        }

        // Verificar el formato de la imagen
        if (!isValidImageFormat(archivo)) {
            throw new MyException("Formato de imagen no válido. Solo se admiten archivos JPG, JPEG y PNG.");
        }

        try {
            Optional<Producto> optionalProducto = productoRepository.findById(id);

            if (optionalProducto.isPresent()) {
                Producto producto = optionalProducto.get();

                Optional<Marca> optionalMarca = marcaRepository.findById(marcaId);

                if (optionalMarca.isPresent()) {
                    Marca marca = optionalMarca.get();

                    producto.setName(name);
                    producto.setPrice(precio);
                    producto.setImg(archivo.getBytes());
                    producto.setMarca(marca);

                    productoRepository.save(producto);
                } else {
                    throw new MyException("Marca no encontrada con el ID: " + marcaId);
                }
            } else {
                throw new MyException("Producto no encontrado con el ID: " + id);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new MyException("Error al actualizar el producto");
        }
    }


    public Producto buscarProducto(Integer id) throws MyException {
        return productoRepository.findById(id)
                .orElseThrow(() -> new MyException("Producto no encontrado con el ID: " + id));
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public void eliminarProducto(Integer id) throws MyException {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        } else {
            throw new MyException("Producto no encontrado con el ID: " + id);
        }
    }

    // Método de utilidad para verificar el formato de la imagen
    private boolean isValidImageFormat(MultipartFile archivo) {
        String contentType = archivo.getContentType();
        return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/jpg") ||
                contentType.equals("image/png"));
    }
}
