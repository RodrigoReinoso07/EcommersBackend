package com.shopzone.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopzone.backend.model.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.categoria = :categoryName")
    public List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT p FROM Product p WHERE p.nombre = :name")
    public List<Product> findByName(@Param("name") String name);
}
