package com.app.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="product_id_seq")
    @SequenceGenerator(name="product_id_seq", sequenceName="product_id_seq", allocationSize=1)
    private long id;
    private String name;
    private String code;
    private boolean allowOrder;
    private boolean sales;
    private Integer countPerPack;

    @CreationTimestamp
    private Timestamp createdDatetime;

    @CreatedBy
    private String createdBy;

    @UpdateTimestamp
    private Timestamp lastModifyDatetime;

    @LastModifiedBy
    private String modifyBy;


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

    public Timestamp getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastModifyDatetime() {
        return lastModifyDatetime;
    }

    public void setLastModifyDatetime(Timestamp lastModifyDatetime) {
        this.lastModifyDatetime = lastModifyDatetime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
}
