package org.example.mendalienproject.controller;

import jakarta.annotation.Nonnull;
import org.example.mendalienproject.model.*;
import org.example.mendalienproject.service.AlienService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aliens")
@CrossOrigin(origins = "http://localhost:3000")
public class AlienRestController {
    private static final Logger logger = LoggerFactory.getLogger(AlienRestController.class);
    private final AlienService alienService;

    public AlienRestController(@Nonnull AlienService alienService) {
        this.alienService = alienService;
    }

    @PostMapping("/newAlien")
    public ResponseEntity<?> addAlien(@RequestBody AlienModel alien) {
        logger.info("Received request to add alien: {}", alien);
        try {
            AlienModel addedAlien = alienService.addAlien(alien);
            return new ResponseEntity<>(addedAlien, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Validation error while adding alien:", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Failed in addAlien:", e);
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AlienModel>> getAllAliens() {
        List<AlienModel> aliens = alienService.getAllAliens();
        logger.debug("Received request to retrieve all aliens: {}", aliens);
        return new ResponseEntity<>(aliens, HttpStatus.OK);
    }

}
