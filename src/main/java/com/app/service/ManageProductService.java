package com.app.service;

import com.app.model.ProductModel;
import com.app.model.ProductModels;
import org.springframework.http.ResponseEntity;

public interface ManageProductService {

    public ResponseEntity<?> createProduct(ProductModel product);

    public ProductModels getProducts();

    public ProductModel getProduct(Long id);

    public ResponseEntity<?> deleteProduct(Long  id);

    public ProductModel updateProduct(ProductModel product, Long id);

}
