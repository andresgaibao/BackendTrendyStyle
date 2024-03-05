package com.proyecto.trendy.repository;

import com.proyecto.trendy.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    Optional<Marca> findById(Integer id);

    void deleteById(Integer id);
}
