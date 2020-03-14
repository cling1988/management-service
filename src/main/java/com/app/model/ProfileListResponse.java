package com.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProfileListResponse  implements Serializable {

    private List<UserModel> results = new ArrayList<>();

    public List<UserModel> getResults() {
        return results;
    }

    public void setResults(List<UserModel> results) {
        this.results = results;
    }

    public void addResults(UserModel model){
        if(null== results){
            results = new ArrayList<>();
        }
        results.add(model);
    }
}
