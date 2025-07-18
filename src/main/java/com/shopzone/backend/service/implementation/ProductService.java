package com.shopzone.backend.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shopzone.backend.model.Product;
import com.shopzone.backend.repository.IProductRepository;
import com.shopzone.backend.service.IProductService;


@Service
public class ProductService implements IProductService {

    private IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> getAll(){
       return productRepository.findAll();
    }
	
	public Product insertOrUpdate(Product productModel){
        return productRepository.save(productModel);
    }
           
	public boolean remove(long id){
        try{
            productRepository.deleteById(id);
        } catch (Exception e){
            return false;
        }
        return true;
    }
	
	public Optional<Product> findById(long id){
        return productRepository.findById(id);
    }
	
	public List<Product> findByCategoryName(String categoryName){
        return productRepository.findByCategoryName(categoryName);
    }
    
    public List<Product> findByName(String name){
        return productRepository.findByName(name);
    }

}
