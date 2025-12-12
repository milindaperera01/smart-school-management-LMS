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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import School.LMS.dto.LoginResponse;
import School.LMS.services.StudentService;
import School.LMS.services.TeacherService;
import School.LMS.services.PrincipalService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private PrincipalService principalService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRes register(@Valid @RequestBody UserRegReq request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody UserLogReq userReq) {
        Users user = new Users();
        user.setUsername(userReq.getUsername());
        user.setPassword(userReq.getPassword());
        String token = userService.verify(user); // returns JWT token
        String role = userService.getRole(user.getUsername()); 
        String username = user.getUsername();
        boolean profileCompleted = false;
        
        switch (role) {
            case "STUDENT":
                profileCompleted = studentService.isProfileCompleted(username);
                break;
            case "TEACHER":
                profileCompleted = teacherService.isProfileCompleted(username);
                break;
            case "PRINCIPAL":
                profileCompleted = principalService.isProfileCompleted(username);
                break;
        }

        return new LoginResponse(token, username,role, profileCompleted);
    }
}
