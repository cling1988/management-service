package com.app.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Outlet extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="outlet_id_seq")
    @SequenceGenerator(name="outlet_id_seq", sequenceName="outlet_id_seq", allocationSize=1)
    private long id;

    private String name;

    private String type;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "outlets")
    private Set<UserProfile> managers;

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

    public Set<UserProfile> getManagers() {
        return managers;
    }

    public void setManagers(Set<UserProfile> managers) {
        this.managers = managers;
    }
}
