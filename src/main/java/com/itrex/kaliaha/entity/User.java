package com.itrex.kaliaha.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @ManyToMany
    @JoinTable(name = "user_role_link",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    public User() {}

    public User(String lastName, String firstName, String login, String password, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.login = login;
        this.password = password;
        this.address = address;
    }

    public User(Long id, String lastName, String firstName, String login, String password, String address) {
        this(lastName, firstName, login, password, address);
        super.setId(id);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) {return true;}
        if(object == null || getClass() != object.getClass()) {return false;}
        User aThat = (User) object;
        if (getId() == null) {
            if (aThat.getId() != null) { return false;}
        } else if (!getId().equals(aThat.getId())) { return false;}

        if (getLastName() == null) {
            if (aThat.getLastName() != null) { return false;}
        } else if (!getLastName().equals(aThat.getLastName())) { return false;}

        if (getFirstName() == null) {
            if (aThat.getFirstName() != null) { return false;}
        } else if (!getFirstName().equals(aThat.getFirstName())) { return false;}

        if (getLogin() == null) {
            if (aThat.getLogin() != null) { return false;}
        } else if (!getLogin().equals(aThat.getLogin())) { return false;}

        if (getPassword() == null) {
            if (aThat.getPassword() != null) { return false;}
        } else if (!getPassword().equals(aThat.getPassword())) { return false;}

        if (getAddress() == null) {
            return aThat.getAddress() == null;
        } else return getAddress().equals(aThat.getAddress());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (getId() != null ? getId().hashCode() : 0);
        result = prime * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = prime * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = prime * result + (getLogin() != null ? getLogin().hashCode() : 0);
        result = prime * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = prime * result + (getAddress() != null ? getAddress().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' +
                "id=" + getId() +
                ", lastName='" + getLastName() + '\'' +
                ", firstName='" + getLastName() + '\'' +
                ", login='" + getLogin() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", address='" + getAddress() + '\'' +
                '}';
    }
}