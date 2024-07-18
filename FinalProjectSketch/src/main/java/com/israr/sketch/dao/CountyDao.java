package com.israr.sketch.dao;

import com.israr.sketch.model.County;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountyDao extends JpaRepository<County, Long> {
    County findByName(String name);
}
