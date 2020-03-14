package com.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OutletListResponse implements Serializable {

    private List<OutletModel> results = new ArrayList<>();


    public List<OutletModel> getResults() {
        return results;
    }

    public void setResults(List<OutletModel> results) {
        this.results = results;
    }

    public void addResults(OutletModel model){
        if(null== results){
            results = new ArrayList<>();
        }
        results.add(model);
    }
}
