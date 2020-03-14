package com.app.service.impl;

import com.app.common.exception.ProductNotFoundException;
import com.app.entity.Product;
import com.app.model.ProductModel;
import com.app.model.ProductListResponse;
import com.app.repository.ProductRepository;
import com.app.service.ManageProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManageProductServiceImpl implements ManageProductService {
    private static final Logger log = LogManager.getLogger(ManageProductServiceImpl.class);

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private ProductRepository productRepo;

    @Override
    public ResponseEntity<?> createProduct(ProductModel product) {
        Product p = modelMapper.map(product,Product.class);
        productRepo.save(p);
        return ResponseEntity.ok(p.getId());
    }

    @Override
    public ResponseEntity<ProductListResponse> getProducts() {
        ProductListResponse models = new ProductListResponse();
        List<Product> productList = productRepo.findAll();
        for(Product p:productList){
            models.addResults(modelMapper.map(p, ProductModel.class));
        }

        return ResponseEntity.ok(models);
    }

    @Override
    public ResponseEntity<ProductModel> getProduct(Long id) {

        Optional<Product> p = productRepo.findById(id);
        p.orElseThrow(()->new ProductNotFoundException(id));
        ProductModel pm= modelMapper.map(p.get(), ProductModel.class);
        return ResponseEntity.ok(pm);
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long id) {
            Optional<Product> p =productRepo.findById(id);
            if(p.isPresent()){
                Product pp= p.get();
                pp.setDeleted(true);
                productRepo.save(pp);
                return ResponseEntity.noContent().build();

            }else{
                throw new ProductNotFoundException(id);
            }

    }

    @Override
    public ResponseEntity<?> updateProduct(ProductModel product, Long id) {

        Product p = modelMapper.map(product,Product.class);
        p.setId(id);
        productRepo.save(p);
//        ProductModel pm= modelMapper.map(p,ProductModel.class);
        return ResponseEntity.ok(p.getId());
    }


}
