package com.test.Superkassa_test.repository;

import com.test.Superkassa_test.entity.SKEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SKRepository extends JpaRepository<SKEntity, Long> {

    List<SKEntity> findProductById(Long id);
}
