package com.shopzone.backend.controller;

import java.util.Base64;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopzone.backend.dto.ProductWithImageRequest;
import com.shopzone.backend.model.Product;
import com.shopzone.backend.service.IProductService;



@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    private IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
        
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(){
    
        List <Product> products = productService.getAll();

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Product> getMethodName(@PathVariable Long id) {
        
        try{
            Product product = productService.findById(id).get();
            if(product != null){
                return ResponseEntity.ok().body(product);
            }
            return ResponseEntity.status(404).build();
        }catch( Exception e) {
            return ResponseEntity.status(404).build();
        } 
        
    }
    

    @PostMapping("/save")
    public  ResponseEntity<Product> saveProduct(@RequestBody ProductWithImageRequest  request ){
        
        if (request == null) {
            return ResponseEntity.status(400).build();
        }
        
        try {
            Product product = new Product();
            product.setCategoria(request.getCategoria());
            product.setDescripcion(request.getDescripcion());
            product.setNombre(request.getNombre());
            product.setPrecio(request.getPrecio());
            product.setStock(request.getStock());

            byte[] imageBytes = Base64.getDecoder().decode(request.getImageData());

            product.setImagen(imageBytes);

            productService.insertOrUpdate(product);

            return ResponseEntity.status(200).build();
        
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    } 

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id){

        if (id <= 0 ) {
            return ResponseEntity.status(400).build();
        }

        if (productService.findById(id).isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        try {
            productService.remove(id);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductWithImageRequest  request){
        
        if (request == null) {
            return ResponseEntity.status(400).build();
        }
        
        if (productService.findById(id).isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        
        try {
            Product productUpdate = productService.findById(id).get();

            productUpdate.setCategoria(request.getCategoria());
            productUpdate.setDescripcion(request.getDescripcion());
            productUpdate.setNombre(request.getNombre());
            productUpdate.setPrecio(request.getPrecio());
            productUpdate.setStock(request.getStock());

            byte[] imageBytes = Base64.getDecoder().decode(request.getImageData());

            productUpdate.setImagen(imageBytes);

            productService.insertOrUpdate(productUpdate);

            return ResponseEntity.status(200).build();
        
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}