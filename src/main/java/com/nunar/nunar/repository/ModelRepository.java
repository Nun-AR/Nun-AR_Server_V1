package com.nunar.nunar.repository;

import com.nunar.nunar.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, String> {
}
