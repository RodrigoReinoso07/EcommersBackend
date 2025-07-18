package com.shopzone.backend.service;

import java.util.List;
import java.util.Optional;

import com.shopzone.backend.model.Product;

public interface IProductService {

    public List<Product> getAll();
	
	public Product insertOrUpdate(Product productModel);
           
	public boolean remove(long id);
	
	public Optional<Product> findById(long id);
	
	public List<Product> findByCategoryName(String categoryName);
    
    public List<Product> findByName(String name);

}
