package com.example.Game.controller;

import com.example.Game.payload.GamePayload;
import com.example.Game.payload.Result;
import com.example.Game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/game")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {
    private final GameService gameService;

    @PostMapping("/")
    public ResponseEntity<Result> saveGame(@RequestBody GamePayload gamePayload) {
        Result result = gameService.saveGame(gamePayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> editGame(@PathVariable("id") UUID id, @RequestBody GamePayload gamePayload) {
        Result result = gameService.editGame(id, gamePayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteGame(@PathVariable("id") UUID id) {
        Result result = gameService.deleteById(id);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

}
