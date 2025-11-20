package com.abhi.dao;

import com.abhi.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMenuRepository extends JpaRepository<MenuItem, Integer> {
}
