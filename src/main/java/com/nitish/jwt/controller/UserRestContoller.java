package com.nitish.jwt.controller;


import com.nitish.jwt.model.User;
import com.nitish.jwt.model.UserRequest;
import com.nitish.jwt.model.UserResponse;
import com.nitish.jwt.service.IUserService;
import com.nitish.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserRestContoller {
    @Autowired
    private IUserService service;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody User user){
        Integer id = service.saveUser(user);
        String body ="User saved" + id + "saved";
    return ResponseEntity.ok(body);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request){
        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                request.getUsername(),request.getPassword()

        ));
        String token =  jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(new UserResponse(token, "Success"));

    }

    @PostMapping("/welcome")
    public ResponseEntity<String> accessData(Principal p ){
        return ResponseEntity.ok("Hello user"+ p.getName() );


    }

}
