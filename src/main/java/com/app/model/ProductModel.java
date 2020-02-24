package com.app.model;

import java.io.Serializable;

public class ProductModel  implements Serializable {

    private long id;
    private String name;
    private String code;
    private boolean allowOrder;
    private boolean sales;
    private Integer countPerPack;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isAllowOrder() {
        return allowOrder;
    }

    public void setAllowOrder(boolean allowOrder) {
        this.allowOrder = allowOrder;
    }

    public boolean isSales() {
        return sales;
    }

    public void setSales(boolean sales) {
        this.sales = sales;
    }

    public Integer getCountPerPack() {
        return countPerPack;
    }

    public void setCountPerPack(Integer countPerPack) {
        this.countPerPack = countPerPack;
    }
}
