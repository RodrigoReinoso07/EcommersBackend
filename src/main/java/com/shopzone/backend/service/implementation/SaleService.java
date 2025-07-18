package com.shopzone.backend.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shopzone.backend.dto.ProductDTO;
import com.shopzone.backend.dto.Product_SaleDTO;
import com.shopzone.backend.dto.SaleDTO;
import com.shopzone.backend.model.Sale;
import com.shopzone.backend.repository.ISaleRepository;
import com.shopzone.backend.service.ISaleService;

@Service
public class SaleService implements ISaleService {

    private ISaleRepository saleRepository;

    public SaleService(ISaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<Sale> getAll() {
        return saleRepository.findAll();
    }

    @Override
    public Sale insertOrUpdate(Sale saleModel) {
        
        return saleRepository.save(saleModel);
    }

    @Override
    public boolean remove(int id) {
        try{
            saleRepository.deleteById(id);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public Optional<Sale> findById(long id) {
        return findById(id);
    }

    @Override
    public List<SaleDTO> findAllByUserId(int id) {
        List<Sale> sales = saleRepository.findAllByUserId(id);

            return sales.stream().map(sale -> {
            SaleDTO saleDTO = new SaleDTO();
            saleDTO.setId(sale.getId());
            saleDTO.setCostoTotal(sale.getCostoTotal());
            saleDTO.setDate(sale.getDate());
            saleDTO.setUser(null); 

            List<Product_SaleDTO> productSaleDTOs = sale.getProducts().stream().map(ps -> {
                Product_SaleDTO psDTO = new Product_SaleDTO();
                
                psDTO.setCantidad(ps.getCantidad());

                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(ps.getProduct().getId());
                productDTO.setNombre(ps.getProduct().getNombre());
                productDTO.setPrecio(ps.getProduct().getPrecio());
                productDTO.setDescripcion(ps.getProduct().getDescripcion());

                psDTO.setProduct(productDTO);
                psDTO.setSale(null);

                return psDTO;
            }).collect(Collectors.toList());

            saleDTO.setProducts(productSaleDTOs);

            return saleDTO;
        }).collect(Collectors.toList());
    }
    
}
