package com.example.orderease.Domain;

public class Favorite {
    String ownerId;
    Foods food;

    Favorite() {}

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Foods getFood() {
        return food;
    }

    public void setFood(Foods food) {
        this.food = food;
    }

    public Favorite(String ownerId, Foods food) {
        this.ownerId = ownerId;
        this.food = food;
    }
}
