package com.Foodease.FoodeaseApp.dao;

import com.Foodease.FoodeaseApp.POJO.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminDao extends JpaRepository<Admin,Long> {
    Admin findByEmailId(@Param("email") String username);

    Optional<Admin> findByRole(String roleName);
}
