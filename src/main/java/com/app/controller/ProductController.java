package com.app.controller;

import com.app.model.ProductModel;
import com.app.model.ProductModels;
import com.app.service.ManageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/service/products")
public class ProductController {
    @Autowired
    private ManageProductService productService;

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody ProductModel model) {
        return productService.createProduct(model);
    }

    @GetMapping()
    @ResponseBody
    public ProductModels getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ProductModel getProduct(@PathVariable Long id){

        return productService.getProduct(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ProductModel replaceEmployee(@RequestBody ProductModel model, @PathVariable Long id) {

        return productService.updateProduct(model,id);
    }
}
