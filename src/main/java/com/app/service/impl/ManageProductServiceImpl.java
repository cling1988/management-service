package com.app.service.impl;

import com.app.config.ProductNotFoundException;
import com.app.entity.Product;
import com.app.model.ProductModel;
import com.app.model.ProductModels;
import com.app.repository.ProductRepository;
import com.app.service.ManageProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManageProductServiceImpl implements ManageProductService {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private ProductRepository productRepo;

    @Override
    public ResponseEntity<?> createProduct(ProductModel product) {
        Product p = modelMapper.map(product,Product.class);
        System.out.println(p);

        p = productRepo.save(p);

        return ResponseEntity.ok(p.getId());
    }

    @Override
    public ProductModels getProducts() {
        ProductModels models = new ProductModels();
        List<Product> productList = productRepo.findAll();
        for(Product p:productList){
            models.addProductModel(modelMapper.map(p, ProductModel.class));
        }

        return models;
    }

    @Override
    public ProductModel getProduct(Long id) {

        Optional<Product> p = productRepo.findById(id);
        p.orElseThrow(()->new ProductNotFoundException(id));

        return modelMapper.map(p.get(), ProductModel.class);
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long id) {
        try {
            productRepo.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new ProductNotFoundException(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @Override
    public ProductModel updateProduct(ProductModel product, Long id) {

        Product p = modelMapper.map(product,Product.class);
        p.setId(id);
        p = productRepo.save(p);
        return modelMapper.map(p,ProductModel.class);
    }


}
