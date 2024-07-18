package com.example.demo.Repository;

import com.example.demo.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Integer> {

    Optional<Item> findByItemname(String filename);


    List<Item> findItemsByRestaurantId(Integer id);
}
