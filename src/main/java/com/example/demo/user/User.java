package com.example.demo.user;

import com.example.demo.restaurant.Restaurant;
import com.example.demo.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH},orphanRemoval = true)
    private List<Token> tokens;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH},orphanRemoval = true)
    private  List<Restaurant> restaurants;

    private String restaurantName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }
}
