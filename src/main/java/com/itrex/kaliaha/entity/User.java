package com.itrex.kaliaha.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper=true)
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

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_link",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();

    @Builder
    public User(Long id, String lastName, String firstName, String login, String password, String address, Set<Role> roles, Set<Order> orders) {
        setId(id);
        this.lastName = lastName;
        this.firstName = firstName;
        this.login = login;
        this.password = password;
        this.address = address;
        this.roles = roles;
        this.orders = orders;
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
}