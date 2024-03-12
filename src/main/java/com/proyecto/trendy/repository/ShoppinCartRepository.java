package com.proyecto.trendy.repository;

import com.proyecto.trendy.entity.ShoppinCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppinCartRepository extends JpaRepository<ShoppinCart, Integer> {
}
