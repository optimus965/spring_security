package com.example.demo;

import com.example.demo.auth.AuthenticationService;
import com.example.demo.auth.RegisterRequest;
import com.example.demo.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class Demo3Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo3Application.class, args);
    }
//    @Bean
//    public CommandLineRunner commandLineRunner(AuthenticationService service) {
//        return args -> {
//            var admin = RegisterRequest.builder().firstname("Admin")
//                    .lastname("Admin").email("hk9262809@gmail.com")
//                    .password("password").role(Role.ADMIN).build();
//            System.out.println("Admin Token: " + service.register(admin).getJwt());
//            var manager = RegisterRequest.builder().firstname("sales")
//                    .lastname("manager").email("hk9262809")
//                    .password("password").role(Role.MANAGER).build();
//            System.out.println("Manager Token: " + service.register(manager).getJwt());
//        };
//    }
}
