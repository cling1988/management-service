package com.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductListResponse implements Serializable {

    @JsonProperty("results")
    private List<ProductModel> results = new ArrayList<>();


    public List<ProductModel> getResults() {
        return results;
    }

    public void setResults(List<ProductModel> results) {
        this.results = results;
    }

    public void addResults(ProductModel model){
        if(null== results){
            results = new ArrayList<>();
        }
        results.add(model);
    }
}
