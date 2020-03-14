package com.app.service;

import com.app.model.ProductModel;
import com.app.model.ProductListResponse;
import org.springframework.http.ResponseEntity;

public interface ManageProductService {

    public ResponseEntity<?> createProduct(ProductModel product);

    public ResponseEntity<ProductListResponse> getProducts();

    public ResponseEntity<ProductModel> getProduct(Long id);

    public ResponseEntity<?> deleteProduct(Long  id);

    public ResponseEntity<?> updateProduct(ProductModel product, Long id);

}
