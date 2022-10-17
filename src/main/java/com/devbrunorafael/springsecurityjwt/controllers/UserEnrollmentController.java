package com.devbrunorafael.springsecurityjwt.controllers;

import com.devbrunorafael.springsecurityjwt.model.Role;
import com.devbrunorafael.springsecurityjwt.model.UserEntity;
import com.devbrunorafael.springsecurityjwt.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserEnrollmentController {

    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAll() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserEntity> getOne(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @PostMapping("/user/save")
    public ResponseEntity<UserEntity> register(@RequestBody UserEntity user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(user));
    }

    /* retirar daqui */
    @PostMapping("/role/save")
    public ResponseEntity<Role> registerRole(@RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveRole(role));
    }

    @DeleteMapping("role/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    @Data
    static class RoleToUserForm{
        private String roleName;
        private String username;
    }
}
