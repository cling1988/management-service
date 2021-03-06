package com.app.model;

import java.io.Serializable;
import java.util.List;

public class OutletModel implements Serializable {

    private long id;

    private String name;

    private String type;

    private String traffic;

    private String restock;

    private String location;

    private List<UserModel> managers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getRestock() {
        return restock;
    }

    public void setRestock(String restock) {
        this.restock = restock;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<UserModel> getManagers() {
        return managers;
    }

    public void setManagers(List<UserModel> managers) {
        this.managers = managers;
    }
}
