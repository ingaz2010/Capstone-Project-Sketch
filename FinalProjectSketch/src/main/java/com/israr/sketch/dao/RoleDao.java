package com.israr.sketch.dao;

import com.israr.sketch.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
