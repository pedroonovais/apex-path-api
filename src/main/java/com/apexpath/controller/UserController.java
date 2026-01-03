package com.apexpath.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apexpath.dto.UserRequestDto;
import com.apexpath.dto.UserUpdateDto;
import com.apexpath.model.User;
import com.apexpath.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserRequestDto userRequest) {
        User createdUser = userService.create(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userUpdateDto));
    }

}
