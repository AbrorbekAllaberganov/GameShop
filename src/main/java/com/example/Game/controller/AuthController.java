package com.example.Game.controller;

import com.example.Game.entity.Parent;
import com.example.Game.payload.LoginPayload;
import com.example.Game.payload.Result;
import com.example.Game.repository.AdminRepository;
import com.example.Game.repository.ParentRepository;
import com.example.Game.security.JwtTokenProvider;

import com.example.Game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    private final GameService gameService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ParentRepository parentRepository;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginPayload payload) {
        Parent parent = parentRepository.findByUserName(payload.getUserName());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getUserName(), payload.getPassword()));
        String token = jwtTokenProvider.createToken(parent.getUserName(), parent.getRoles());

        if (token == null) {
            return new ResponseEntity("Login or password is invalid", HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> login = new HashMap<>();
        login.put("token", token);
        login.put("username", parent.getUserName());
        login.put("success", true);
        return ResponseEntity.ok(login);
    }

    @GetMapping("/game")
    public ResponseEntity<Result> getAll(){
        Result result=gameService.getAll();
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<Result> findGame(@PathVariable("id")UUID id){
        Result result=gameService.getById(id);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }



}
