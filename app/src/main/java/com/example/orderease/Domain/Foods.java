package com.example.orderease.Domain;

import java.io.Serializable;

public class Foods implements Serializable {
    private int CategoryId;
    private  String Description;
    private boolean BestFood;
    private int Id ;
    private int LocationId;
    private double Price;
    private String ImagePath;
    private int PriceId;
    private double Star;
    private int TimeId;
    private int TimeValue;
    private String Title;
    private int numberInCart;

    public Foods() {
    }


    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public boolean isBestFood() {
        return BestFood;
    }

    public void setBestFood(boolean BestFood) {
        this.BestFood = BestFood;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int LocationId) {
        this.LocationId = LocationId;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String ImagePath) {
        this.ImagePath = ImagePath;
    }

    public int getPriceId() {
        return PriceId;
    }

    public void setPriceId(int PriceId) {
        this.PriceId = PriceId;
    }

    public double getStar() {
        return Star;
    }

    public void setStar(double Star) {
        this.Star = Star;
    }

    public int getTimeId() {
        return TimeId;
    }

    public void setTimeId(int TimeId) {
        this.TimeId = TimeId;
    }

    public int getTimeValue() {
        return TimeValue;
    }

    public void setTimeValue(int TimeValue) {
        this.TimeValue = TimeValue;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
