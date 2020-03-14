package com.app.controller;

import com.app.common.CommonKey;
import com.app.model.ProductModel;
import com.app.model.ProductListResponse;
import com.app.service.ManageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path= CommonKey.URL_PRODUCT)
public class ProductController {
    @Autowired
    private ManageProductService productService;

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody ProductModel model) {
        return productService.createProduct(model);
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<ProductListResponse> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ProductModel> getProduct(@PathVariable Long id){

        return productService.getProduct(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateProduct(@RequestBody ProductModel model, @PathVariable Long id) {
        return productService.updateProduct(model,id);
    }
}
