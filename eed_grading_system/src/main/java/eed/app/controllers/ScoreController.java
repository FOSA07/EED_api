package eed.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eed.app.models.Score;
import eed.app.services.ScoreService;

@RestController
@RequestMapping("/api/score")
public class ScoreController {
    
    private ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping(path= "/updateScore", consumes = "application/json")
    public ResponseEntity<?> createStudent(@RequestBody Score scoreRequest) throws Exception {

        try{
            Score score = scoreService.addStudents(scoreRequest);

            return ResponseEntity.ok(score);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }
}
