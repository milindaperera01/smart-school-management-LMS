package School.LMS.controller;

import School.LMS.dto.BatchMarksAdd;
import School.LMS.dto.MarksAdd;
import School.LMS.services.MarksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marks")
public class MarksController {

    @Autowired
    private MarksService marksService;

    // Existing Single Save
    @PostMapping("/add/mark")
    @ResponseStatus(HttpStatus.CREATED)
    public String addMark(@Valid @RequestBody MarksAdd request) {
        return marksService.addMark(request);
    }

    // New Batch Save
    @PostMapping("/add/batch")
    public ResponseEntity<String> addBatchMarks(@Valid @RequestBody BatchMarksAdd request) {
        try {
            System.out.println("Received batch marks request: ");
            String response = marksService.addBatchMarks(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}