package com.example.demo.auth;

import com.example.demo.Repository.RestaurantRepository;
import com.example.demo.Repository.userRepository;
import com.example.demo.config.JwtService;
import com.example.demo.restaurant.Restaurant;
import com.example.demo.token.Token;
import com.example.demo.token.TokenRepository;
import com.example.demo.token.TokenType;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final userRepository  repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final userRepository userRepository1;
    private final AuthenticationManager authenticationManager;
    private final RestaurantRepository restaurantRepository;
    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> user1 = userRepository1.findByEmail(request.getEmail());
        if(user1.isPresent()) {
            return null;
        }
        Optional<Restaurant> restaurant = restaurantRepository.findByRestaurantName(request.getRestaurantname());
        if(restaurant.isPresent()) {
            return  null;
        }
        var user = User.builder().firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail()).restaurantName(request.getRestaurantname())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole()).build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        savedUserToken(user,jwtToken);

        if(request.getRestaurantname() != null && request.getLocation() != null) {
            savedUserRestaurant(user,request.getRestaurantname(), request.getLocation());
        }
        return AuthenticationResponse.builder().jwt(jwtToken).build();
    }
    private void savedUserToken(User user,String jwtToken) {
        var token = Token.builder().user(user).token(jwtToken).tokenType(TokenType.BEARER)
                .expired(false).build();
        tokenRepository.save(token);
    }
    private void savedUserRestaurant(User user,String restaurantName,String location) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(restaurantName);
        restaurant.setUser(user);
        restaurant.setLocation(location);
        restaurantRepository.save(restaurant);


    }


    public AuthenticationResponse authenticate(AutheticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow(()->new UsernameNotFoundException("user name not found"));
        var jwtToken = jwtService.generateToken(user);
        savedUserToken(user,jwtToken);
        return AuthenticationResponse.builder().jwt(jwtToken).build();
    }
}
