package com.shopzone.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopzone.backend.model.Sale;

@Repository
public interface ISaleRepository extends JpaRepository<Sale, Integer>{

    @Query("SELECT s FROM Sale s JOIN FETCH s.products JOIN FETCH s.user WHERE s.user.id = :id")
    public abstract List<Sale> findAllByUserId(@Param("id") int id);

}
