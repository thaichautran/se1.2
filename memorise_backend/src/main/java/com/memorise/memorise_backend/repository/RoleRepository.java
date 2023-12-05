package com.memorise.memorise_backend.repository;

import com.memorise.memorise_backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
