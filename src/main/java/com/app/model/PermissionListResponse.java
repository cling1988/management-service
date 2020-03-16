package com.app.model;

import java.util.ArrayList;
import java.util.List;

public class PermissionListResponse {

    private List<PermissionModel> results = new ArrayList<>();

    public List<PermissionModel> getResults() {
        return results;
    }

    public void setResults(List<PermissionModel> results) {
        this.results = results;
    }

    public void addResults(PermissionModel model){
        if(null== results){
            results = new ArrayList<>();
        }
        results.add(model);
    }

}
