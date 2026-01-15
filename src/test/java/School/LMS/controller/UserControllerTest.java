package School.LMS.controller;

import School.LMS.dto.LoginResponse;
import School.LMS.dto.UserLogReq;
import School.LMS.dto.UserRegReq;
import School.LMS.dto.UserRes;
import School.LMS.models.Users;
import School.LMS.services.JWTservice;
import School.LMS.services.MyUserDetailService;
import School.LMS.services.PrincipalService;
import School.LMS.services.StudentService;
import School.LMS.services.TeacherService;
import School.LMS.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private StudentService studentService;
    @MockBean
    private TeacherService teacherService;
    @MockBean
    private PrincipalService principalService;
    @MockBean
    private JWTservice jwtservice;
    @MockBean
    private MyUserDetailService myUserDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_Success() throws Exception {
        UserRegReq req = new UserRegReq();
        req.setUsername("newuser");
        req.setPassword("password123");
        req.setRole("STUDENT");

        UserRes res = new UserRes(1L, "newuser", "STUDENT");

        when(userService.register(any(UserRegReq.class))).thenReturn(res);

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newuser"));
    }

    @Test
    void login_Success() throws Exception {
        UserLogReq req = new UserLogReq();
        req.setUsername("student1");
        req.setPassword("pass");

        when(userService.verify(any(Users.class))).thenReturn("token");
        when(userService.getRole("student1")).thenReturn("STUDENT");
        when(studentService.isProfileCompleted("student1")).thenReturn(true);

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"))
                .andExpect(jsonPath("$.role").value("STUDENT"))
                .andExpect(jsonPath("$.profileCompleted").value(true));
    }
}
