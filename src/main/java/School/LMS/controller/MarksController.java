package School.LMS.controller;

import School.LMS.dto.MarksAdd;
import School.LMS.services.MarksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marks")
public class MarksController {

    @Autowired
    private MarksService marksService;

    @PostMapping("/add/mark")
    @ResponseStatus(HttpStatus.CREATED)
    public String addMark(@Valid @RequestBody MarksAdd request) {
        return marksService.addMark(request);
    }

}
