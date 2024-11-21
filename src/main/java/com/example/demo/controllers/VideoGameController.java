package com.example.demo.controllers;

import com.example.demo.models.VideoGame;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/games")
public class VideoGameController {

    Logger logger = LoggerFactory.getLogger(VideoGameController.class);
    List<VideoGame> VideoGameList = new ArrayList<>();

    @GetMapping()
    public ResponseEntity<List<VideoGame>> getAllVideoGames(){
        return ResponseEntity.ok(VideoGameList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoGame> getVideoGame(@PathVariable String id){
        Optional<VideoGame> firstVideoGame = VideoGameList
                .stream()
                .filter(d -> d.getId().toString().equals(id))
                .findFirst();

        if (firstVideoGame.isPresent()) {
            return ResponseEntity.ok(firstVideoGame.get());
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoGame> updateVideoGame(@Valid @RequestBody VideoGame request, @PathVariable String id){
        Optional<VideoGame> firstVideoGame = VideoGameList
                .stream()
                .filter(d -> d.getId().toString().equals(id))
                .findFirst();

        if (firstVideoGame.isPresent()) {
            VideoGameList.remove(firstVideoGame.get());
            VideoGameList.add(request);
            return ResponseEntity.ok(request);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<VideoGame> createVideoGame(@Valid @RequestBody VideoGame request) {
        request.setId(UUID.randomUUID());
        logger.info("Created VideoGame with ID: {}", request.getId());
        VideoGameList.add(request);
        return ResponseEntity.ok(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VideoGame> deleteVideoGame(@PathVariable String id) {
        Optional<VideoGame> firstVideoGame = VideoGameList
                .stream()
                .filter(d -> d.getId().toString().equals(id))
                .findFirst();

        if (firstVideoGame.isPresent()) {
            VideoGameList.remove(firstVideoGame.get());
            return ResponseEntity.ok(firstVideoGame.get());
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
