package com.example.demo.Repository;

import com.example.demo.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {


    Optional<Restaurant> findByRestaurantName(String string);

    void deleteRestaurantById(Integer id);
    void deleteRestaurantByUserId(Integer id);
//    @Modifying
//    @Query("DELETE FROM Restaurant r WHERE r.id = :restaurantId")
//    void deleteById(@Param("restaurantId") Integer restaurantId);
}
