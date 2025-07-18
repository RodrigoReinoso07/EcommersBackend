package com.shopzone.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopzone.backend.config.JwtUtil;
import com.shopzone.backend.dto.ProductsIntoCart;
import com.shopzone.backend.dto.SaleDTO;
import com.shopzone.backend.model.Product;
import com.shopzone.backend.model.Product_Sale;
import com.shopzone.backend.model.Sale;
import com.shopzone.backend.model.User;
import com.shopzone.backend.service.IProductService;
import com.shopzone.backend.service.ISaleService;
import com.shopzone.backend.service.IUserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private ISaleService saleService;
    private IProductService productService;
    private IUserService userService;
    private JwtUtil jwtUtil;

    public SaleController(ISaleService saleService, IProductService productService, JwtUtil jwtUtil, IUserService userService) {
        this.saleService = saleService;
        this.productService = productService;   
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Sale>> getAllSales() {
        List <Sale> sales = saleService.getAll();

        if (sales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok().body(sales);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Sale> findSale(@PathVariable Long id) {
        
        try{
            Sale sale = saleService.findById(id).get();
            if(sale != null){
                return ResponseEntity.ok().body(sale);
            }
            return ResponseEntity.status(404).build();
        }catch( Exception e) {
            return ResponseEntity.status(404).build();
        } 
        
    }

    @GetMapping("/allMySales")
    public ResponseEntity<List<SaleDTO>> findSalesFromId(@CookieValue(value = "token",defaultValue = "", required = false) String token) {
        try{
            String username = jwtUtil.extractUsername(token);
            User user = userService.findByUsername(username);
            int id = user.getId();
            
            List <SaleDTO> sales = saleService.findAllByUserId(id);
            if(sales != null){
                return ResponseEntity.ok().body(sales);
            }

            return ResponseEntity.status(404).build();

        }catch( Exception e) {
            return ResponseEntity.status(404).build();
        } 
        
    }

    
    
    @PostMapping("/save")
    public  ResponseEntity<Sale> saveProduct(@RequestBody List<ProductsIntoCart> products,
                                             @CookieValue(value = "token",defaultValue = "", required = false) String token) {
        
        if (products == null) {
            return ResponseEntity.status(400).build();
        }
        
        double totalCost = 0.0;

        try {

            String username = jwtUtil.extractUsername(token);
            User user = userService.findByUsername(username);
            Sale sale = new Sale();
            sale.setDate(LocalDate.now());

            List<Product_Sale> productSales = new ArrayList<>();

            for (ProductsIntoCart item : products) {
    
                Optional<Product> productOpt = productService.findById(item.getId());
                Product product = productOpt.get();

                if (product != null) {
                    totalCost += product.getPrecio() * item.getQuantity();

                    Product_Sale ps = new Product_Sale();
                    ps.setProduct(productOpt.get());
                    ps.setCantidad(item.getQuantity());
                    ps.setSale(sale);
    
                    productSales.add(ps);
                  
                }
            }

            sale.setCostoTotal(totalCost);
            sale.setProducts(productSales);
            sale.setUser(user);
            saleService.insertOrUpdate(sale);
            return ResponseEntity.status(201).build();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).build();
        }
        
    } 
 
}
