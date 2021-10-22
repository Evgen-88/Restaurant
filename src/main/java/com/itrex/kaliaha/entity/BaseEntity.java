package com.itrex.kaliaha.entity;

import java.io.Serializable;

public class BaseEntity<PK extends Serializable> {
    private PK id;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }
}
