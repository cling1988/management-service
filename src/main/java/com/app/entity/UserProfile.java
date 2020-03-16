package com.app.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class UserProfile extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="profile_id_seq")
    @SequenceGenerator(name="profile_id_seq", sequenceName="profile_id_seq", allocationSize=1)
    private long id;

    private String name;

    private String employeeId;

    private String title;

    @Column(length = 20)
    private String contact;

    private String email;

    @Column(length = 10)
    private String birthday;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "outletManager",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "outlet_id")
    )
    private Set<Outlet> outlets;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private LoginUser loginUser;

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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Set<Outlet> getOutlets() {
        return outlets;
    }

    public void setOutlets(Set<Outlet> outlets) {
        this.outlets = outlets;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}
