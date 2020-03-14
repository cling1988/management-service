package com.app.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="role_id_seq")
    @SequenceGenerator(name="role_id_seq", sequenceName="role_id_seq", allocationSize=1)
    private long id;

    @Column(length = 100)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "rolePermissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<LoginUser> loginUsers;

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

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<LoginUser> getLoginUsers() {
        return loginUsers;
    }

    public void setLoginUsers(Set<LoginUser> loginUsers) {
        this.loginUsers = loginUsers;
    }
}
