package com.israr.sketch.dao;

import com.israr.sketch.model.County;
import com.israr.sketch.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectionDao extends JpaRepository<Election, Long> {
    List<Election> findByPosition(String role);
    //List<Election> findByElectionsOrderByCounties(County county);
    List<Election> findByIsActive(boolean isActive);
}
