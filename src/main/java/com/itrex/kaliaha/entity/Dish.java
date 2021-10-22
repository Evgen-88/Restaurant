package com.itrex.kaliaha.entity;

import com.itrex.kaliaha.entity.util.Group;

public class Dish extends BaseEntity<Long> {
    private String dishName;
    private int price;
    private Group group;
    private String description;
    private String imagePath;

    public Dish() {}

    public Dish (String dishName, int price, Group group, String description, String imagePath) {
        this.dishName = dishName;
        this.price = price;
        this.group = group;
        this.description = description;
        this.imagePath = imagePath;
    }

    public Dish(Long id, String dishName, int price, Group group, String description, String imagePath) {
        this(dishName, price, group, description, imagePath);
        super.setId(id);
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) {return true;}
        if(object == null || getClass() != object.getClass()) {return false;}
        Dish aThat = (Dish) object;
        if(getPrice() != aThat.getPrice()) {return false;}

        if (getId() == null) {
            if (aThat.getId() != null) { return false;}
        } else if (!getId().equals(aThat.getId())) { return false;}

        if (getDishName() == null) {
            if (aThat.getDishName() != null) { return false;}
        } else if (!getDishName().equals(aThat.getDishName())) { return false;}

        if (getGroup() == null) {
            if (aThat.getGroup() != null) { return false;}
        } else if (!getGroup().equals(aThat.getGroup())) { return false;}

        if (getDescription() == null) {
            if (aThat.getDescription() != null) { return false;}
        } else if (!getDescription().equals(aThat.getDescription())) { return false;}

        if (getImagePath() == null) {
            return aThat.getImagePath() == null;
        } else return getImagePath().equals(aThat.getImagePath());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + getPrice();
        result = prime * result + (getId() != null ? getId().hashCode() : 0);
        result = prime * result + (getDishName() != null ? getDishName().hashCode() : 0);
        result = prime * result + (getGroup() != null ? getGroup().hashCode() : 0);
        result = prime * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = prime * result + (getImagePath() != null ? getImagePath().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + getId() +
                ", dishName='" + dishName + '\'' +
                ", price=" + price +
                ", group=" + group +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}