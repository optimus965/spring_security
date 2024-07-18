package com.example.demo.controller;

import com.example.demo.Repository.ItemRepository;
import com.example.demo.Repository.RestaurantRepository;
import com.example.demo.item.Item;
import com.example.demo.item.ItemResponse;
import com.example.demo.item.ItemService;
import com.example.demo.restaurant.RegisterRestaurant;
import com.example.demo.restaurant.Restaurant;
import com.example.demo.token.TokenRepository;
import com.example.demo.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Repository.userRepository;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/admin")
@RestController
@RequiredArgsConstructor
@Transactional
public class AdminController {
    private final userRepository userRepository1;
    private final TokenRepository tokenRepository;
    private final RestaurantRepository restaurantRepository;
    private final ItemRepository itemRepository;
    private  final ItemService itemService;

    @PostMapping("/restaurant")
    public ResponseEntity<Restaurant> restaurantPost(@RequestBody RegisterRestaurant restaurant) {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setRestaurantName(restaurant.getRestaurantName());
        restaurant1.setLocation(restaurant.getLocation());
        restaurantRepository.save(restaurant1);
        return ResponseEntity.ok(restaurant1);
    }

    @GetMapping
    public String get() {
        return "GET :: ADMIN CONTROLLER";
    }

//    @PostMapping
//    public String post() {
//        return "POST :: ADMIN CONTROLLER";
//    }

    @PutMapping
    public String put() {
        return "PUT :: ADMIN CONTROLLER";
    }


    @DeleteMapping
    public String delete() {
        return "delete :: ADMIN CONTROLLER";
    }

    @DeleteMapping("/{id}")
    public String deleteManger(@PathVariable Integer id) {
//        tokenRepository.deleteTokensByUserId(id);
        userRepository1.deleteById(id);

        return "User with id " + id + "has been deleted  " + "and also tokens related to him are also successfully deleted";
    }

    @PostMapping("/addItem")
    public String addItem(@RequestParam("itemImage") MultipartFile itemImage, @RequestParam("itemPrice") String itemPrice,@RequestParam("itemName") String itemName, Principal connectedUser) throws Exception {
        var user =(User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        String string =  user.getEmail() + itemName;
        Optional<Item> item = itemRepository.findByItemname(string);
        if(item.isPresent()) {
            return "item was not added as it is already present";
        }
        itemService.UploadImage(itemImage ,itemPrice,user,string);
        return "Item Added Succefully";
    }


    @GetMapping("/getItem/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = itemService.downloadImage(fileName);
        Optional<Item> item = itemRepository.findByItemname(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }
    @DeleteMapping("/deleteR/{id}")
    @Transactional
    public String deleteRestaurant(@PathVariable Integer id) {
        restaurantRepository.deleteById(id);
        return "the user has been deleted Successfully";
    }
    @GetMapping("/items")
    public ResponseEntity<List<ItemResponse>> Items(Principal connectedUser) {
         var user =(User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Optional<Restaurant> restaurant = restaurantRepository.findByRestaurantName(user.getRestaurantName());
        List<Item> item = itemRepository.findItemsByRestaurantId(restaurant.get().getId());
        List<ItemResponse> Responses = new ArrayList<>();
        int length = user.getEmail().length();
        for (Item c:item) {
            byte[] imageData = itemService.downloadImage(c.getItemname());
            ItemResponse itemResponse = new ItemResponse();
            itemResponse.setItemName(c.getItemname().substring(length));
            itemResponse.setImageData(imageData);
            itemResponse.setPrice(c.getPrice());
            Responses.add(itemResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(Responses);
    }
    @DeleteMapping("/deleteItem/{id}")
    public String itemDeletedSuccessfully(@PathVariable Integer id) {
        itemRepository.deleteById(id);
        return "item deleted successfully";
    }
}






