package com.example.demo.restaurant;

import com.example.demo.item.Item;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Data
@RequiredArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String restaurantName;

    private String Location;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "restaurant",orphanRemoval = true,cascade = {CascadeType.REFRESH})
    private List<Item> item;









}
