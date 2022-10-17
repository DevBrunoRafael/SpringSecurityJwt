package com.devbrunorafael.springsecurityjwt;

import com.devbrunorafael.springsecurityjwt.model.Role;
import com.devbrunorafael.springsecurityjwt.model.UserEntity;
import com.devbrunorafael.springsecurityjwt.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;


@SpringBootApplication
public class SpringSecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.registerUser(new UserEntity(null, "John Travolta", "John", "123", new ArrayList<>()));
            userService.registerUser(new UserEntity(null, "Will Smith", "Will", "123", new ArrayList<>()));
            userService.registerUser(new UserEntity(null, "Jim Carry", "Jim", "123", new ArrayList<>()));
            userService.registerUser(new UserEntity(null, "Arnold nunes", "arnold", "123", new ArrayList<>()));

            userService.addRoleToUser("John", "ROLE_USER");
            userService.addRoleToUser("John", "ROLE_MANAGER");

            userService.addRoleToUser("Will", "ROLE_MANAGER");

            userService.addRoleToUser("Jim", "ROLE_ADMIN");

            userService.addRoleToUser("arnold", "ROLE_USER");
            userService.addRoleToUser("arnold", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("arnold", "ROLE_ADMIN");
        };
    }

}
