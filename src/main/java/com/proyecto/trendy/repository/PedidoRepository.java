package com.proyecto.trendy.repository;

import com.proyecto.trendy.entity.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedidos, Integer> {
}
