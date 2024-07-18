package com.example.demo.item;

import com.example.demo.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String itemname;

    private String type;


    private String price;
    @Lob
    @Column(name="imagedata",length = 1000)
    private byte[] imageData;
}
