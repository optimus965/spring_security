package com.example.demo.item;

import lombok.Data;

@Data
public class ItemResponse {
    private String itemName;
    private String price;
    private byte[] imageData;
}
