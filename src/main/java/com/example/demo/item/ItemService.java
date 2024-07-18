package com.example.demo.item;

import com.example.demo.Repository.ItemRepository;
import com.example.demo.Repository.RestaurantRepository;
import com.example.demo.restaurant.Restaurant;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final RestaurantRepository restaurantRepository;


    public String UploadImage(MultipartFile file, String price, User user,String itemName) throws IOException {
        Optional<Restaurant> restaurant = restaurantRepository.findByRestaurantName(user.getRestaurantName());
        if(restaurant.isEmpty()) {
            return null;
        }
       Item item = itemRepository.save(Item.builder()
               .itemname(itemName).type(file.getContentType()).price(price)
                       .restaurant(restaurant.get())
               .imageData(ImageCoAndDe.compressImage(file.getBytes())).build());
        return "file uploaded Successfully: " + itemName;
    }

    public byte[] downloadImage(String fileName) {
        Optional<Item> item = itemRepository.findByItemname(fileName);
        byte[] image = ImageCoAndDe.decompressImage(item.get().getImageData());
        return image;
    }
}
