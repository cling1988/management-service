package com.app.controller;

import com.app.common.CommonKey;
import com.app.model.ProductModel;
import com.app.model.ProductListResponse;
import com.app.service.ManageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ProductController {
    @Autowired
    private ManageProductService productService;

    @PostMapping(CommonKey.URL_PRODUCT_EDIT)
    public ResponseEntity<?> createProduct(@RequestBody ProductModel model) {
        return productService.createProduct(model);
    }

    @GetMapping(CommonKey.URL_PRODUCT_VIEW)
    public ResponseEntity<ProductListResponse> getProducts() {
        return productService.getProducts();
    }

    @GetMapping(CommonKey.URL_PRODUCT_VIEW+"/{id}")
    public ResponseEntity<ProductModel> getProduct(@PathVariable Long id){
        return productService.getProduct(id);
    }

    @DeleteMapping(CommonKey.URL_PRODUCT_EDIT+"/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PutMapping(CommonKey.URL_PRODUCT_EDIT+"/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductModel model, @PathVariable Long id) {
        return productService.updateProduct(model,id);
    }
}
