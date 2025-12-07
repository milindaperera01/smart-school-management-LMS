package School.LMS.controller;


import School.LMS.dto.UserLogReq;
import School.LMS.dto.UserRegReq;
import School.LMS.dto.UserRes;
import School.LMS.models.Users;
import School.LMS.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRes register(@Valid @RequestBody UserRegReq request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@Valid @RequestBody UserLogReq userReq) {
        Users user = new Users();
        user.setUsername(userReq.getUsername());
        user.setPassword(userReq.getPassword());
        return userService.verify(user);
    }
}
