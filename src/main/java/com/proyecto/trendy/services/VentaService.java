package com.proyecto.trendy.services;

import com.proyecto.trendy.entity.Producto;
import com.proyecto.trendy.entity.User;
import com.proyecto.trendy.entity.Venta;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.repository.ProductoRepository;
import com.proyecto.trendy.repository.UserRepository;
import com.proyecto.trendy.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final UserRepository userRepository;
    private final ProductoRepository productoRepository;

    public void registrarVenta(Integer userId, List<Integer> productoIds, Double total) throws MyException {
        // Validar que los campos no estén vacíos
        if (userId == null || productoIds == null || productoIds.isEmpty() || total == null) {
            throw new MyException("Todos los campos son obligatorios");
        }

        try {
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                List<Producto> productos = productoRepository.findAllById(productoIds);

                Venta venta = Venta.builder()
                        .user(user)
                        .productos(productos)
                        .total(total)
                        .build();

                // Calcular el total sumando los precios de los productos
                double totalCalculado = productos.stream().mapToDouble(Producto::getPrice).sum();
                if (totalCalculado != total) {
                    throw new MyException("El total proporcionado no coincide con la suma de los precios de los productos.");
                }

                ventaRepository.save(venta);
            } else {
                throw new MyException("Usuario no encontrado con el ID: " + userId);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new MyException("Error al registrar la venta");
        }
    }


    public void actualizarVenta(Integer id, Integer userId, List<Integer> productoIds, Double total)
            throws MyException, Exception {
        // Validar que los campos no estén vacíos
        if (userId == null || productoIds == null || productoIds.isEmpty() || total == null) {
            throw new MyException("Todos los campos son obligatorios");
        }

        Optional<Venta> optionalVenta = ventaRepository.findById(id);

        if (optionalVenta.isPresent()) {
            Venta venta = optionalVenta.get();

            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                List<Producto> productos = productoRepository.findAllById(productoIds);

                venta.setUser(user);
                venta.setProductos(productos);
                venta.setTotal(total);

                ventaRepository.save(venta);
            } else {
                throw new MyException("Usuario no encontrado con el ID: " + userId);
            }
        } else {
            throw new MyException("Venta no encontrada con el ID: " + id);
        }
    }


    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public Venta leerVenta(Integer id) throws MyException {
        Optional<Venta> optionalVenta = ventaRepository.findById(id);

        if (optionalVenta.isPresent()) {
            return optionalVenta.get();
        } else {
            throw new MyException("Venta no encontrada con el ID: " + id);
        }
    }


    public void eliminarVenta(Integer id) throws MyException {
        Optional<Venta> optionalVenta = ventaRepository.findById(id);

        if (optionalVenta.isPresent()) {
            ventaRepository.deleteById(id);
        } else {
            throw new MyException("Venta no encontrada con el ID: " + id);
        }
    }

}
