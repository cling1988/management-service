package com.app.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Permission extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="permission_id_seq")
    @SequenceGenerator(name="permission_id_seq", sequenceName="permission_id_seq", allocationSize=1)
    private long id;

    @Column(length = 100)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
    private Set<Role> roles;

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
