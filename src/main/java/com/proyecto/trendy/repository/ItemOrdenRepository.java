package com.proyecto.trendy.repository;

import com.proyecto.trendy.entity.ItemOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOrdenRepository extends JpaRepository<ItemOrden, Integer> {
}
