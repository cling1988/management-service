package com.app.model;

import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Validated
public class ProductModels  implements Serializable {

    private List<ProductModel> productModels = new ArrayList<>();


    public List<ProductModel> getProductModels() {
        return productModels;
    }

    public void setProductModels(List<ProductModel> productModels) {
        this.productModels = productModels;
    }

    public void addProductModel(ProductModel model){
        if(null==productModels){
            productModels = new ArrayList<>();
        }
        productModels.add(model);
    }
}
