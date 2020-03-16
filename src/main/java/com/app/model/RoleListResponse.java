package com.app.model;

import java.util.ArrayList;
import java.util.List;

public class RoleListResponse {

    private List<RoleModel> results = new ArrayList<>();

    public List<RoleModel> getResults() {
        return results;
    }

    public void setResults(List<RoleModel> results) {
        this.results = results;
    }

    public void addResults(RoleModel model){
        results.add(model);
    }
}
