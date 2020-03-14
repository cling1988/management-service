package com.app.entity;

import com.app.common.ApplicationType;

import javax.persistence.*;
import java.util.Set;

@Entity
public class LoginUser extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="login_id_seq")
    @SequenceGenerator(name="login_id_seq", sequenceName="login_id_seq", allocationSize=1)
    private long id;
    private String username;
    private String password;
    private boolean active;

    @Enumerated(EnumType.STRING)
    private ApplicationType type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "userRoles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ApplicationType getType() {
        return type;
    }

    public void setType(ApplicationType type) {
        this.type = type;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
