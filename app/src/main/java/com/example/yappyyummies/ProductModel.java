package com.example.yappyyummies;

import android.widget.Button;

public class ProductModel {
    int id;
    String Name;
    String Brand;
    String Type;
    String Age;
    double Price;
    String image;


    public ProductModel( int id,String name, String brand, String type, String age, double price, String image) {
        this.id = id;
        Name = name;
        Brand = brand;
        Type = type;
        Age = age;
        Price = price;
        this.image = image;


    }
    public int getId(){
        return id;
    }
    public String getName(){
        return Name;
    }

    public String getBrand() {
        return Brand;
    }

    public String getType() {
        return Type;
    }

    public String getAge() {
        return Age;
    }

    public double getPrice() {
        return Price;
    }

    public String getImage() {
        return image;
    }
}
