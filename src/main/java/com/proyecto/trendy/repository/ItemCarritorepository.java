package com.proyecto.trendy.repository;

import com.proyecto.trendy.entity.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCarritorepository extends JpaRepository<ItemCarrito, Integer> {
}
