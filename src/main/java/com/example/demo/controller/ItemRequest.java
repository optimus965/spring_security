package com.example.demo.controller;

import lombok.Data;

@Data
public class ItemRequest {

    private String jwt;

    private String name;

    private String price;

    private byte[] imageData;

//    private String image;

//    private String restaurantname;

}
