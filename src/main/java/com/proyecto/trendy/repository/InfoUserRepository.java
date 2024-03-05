package com.proyecto.trendy.repository;

import com.proyecto.trendy.entity.InfoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoUserRepository extends JpaRepository<InfoUser, Integer> {
}
