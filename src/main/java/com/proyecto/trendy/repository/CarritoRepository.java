package com.proyecto.trendy.repository;

import com.proyecto.trendy.entity.Carrito;
import com.proyecto.trendy.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

}
