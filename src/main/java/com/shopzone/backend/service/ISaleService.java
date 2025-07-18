package com.shopzone.backend.service;

import java.util.List;
import java.util.Optional;

import com.shopzone.backend.dto.SaleDTO;
import com.shopzone.backend.model.Sale;

public interface ISaleService {

    public List<Sale> getAll();

    public List<SaleDTO> findAllByUserId(int id);
	
	public Sale insertOrUpdate(Sale saleModel);
           
	public boolean remove(int id);
	
	public Optional<Sale> findById(long id);
    
}
