package com.example.chaos.project;

/**
 * Created by Lydia on 2016-04-11.
 */
public class Cart {
    public int _id;
    public String item_name;
    public int count;
    public float price;

    public Cart() {
    }

    public Cart(String item_name, int count, float price) {
        this.item_name = item_name;
        this.count = count;
        this.price = price;
    }
}
