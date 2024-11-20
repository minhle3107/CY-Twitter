package com.global.project.repository;

import com.global.project.entity.Hastag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HastagRepository extends JpaRepository<Hastag, String> {
    boolean existsByName(String name);

    Hastag findByName(String name);
}
