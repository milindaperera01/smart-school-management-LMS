package School.LMS.controller;

import School.LMS.dto.BatchMarksAdd;
import School.LMS.dto.MarksAdd;
import School.LMS.dto.StudentExamMarkDTO;
import School.LMS.services.MarksService;
import School.LMS.services.JWTservice;
import School.LMS.services.MyUserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MarksController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MarksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarksService marksService;

    @MockBean
    private JWTservice jwtservice;
    
    @MockBean
    private MyUserDetailService myUserDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addMark_Success() throws Exception {
        MarksAdd req = new MarksAdd();
        req.setStudentId(1L);
        req.setScore(90.0);
        req.setExamName("Exam 1");
        req.setClassId(10L);
        req.setSubjectId(5L);

        when(marksService.addMark(any(MarksAdd.class))).thenReturn("Marks added successfully");

        mockMvc.perform(post("/marks/add/mark")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Marks added successfully"));
    }

    @Test
    void addBatchMarks_Success() throws Exception {
        BatchMarksAdd req = new BatchMarksAdd();
        req.setClassroom("10-A");
        req.setSubject("Math");
        req.setExam_name("Final");
        Map<String, Double> marks = new HashMap<>();
        marks.put("1", 99.0);
        req.setMarks(marks);

        when(marksService.addBatchMarks(any(BatchMarksAdd.class))).thenReturn("Batch added");

        mockMvc.perform(post("/marks/add/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Batch added"));
    }

    @Test
    void getMarksForStudentExam_Success() throws Exception {
        List<StudentExamMarkDTO> marks = List.of(
                new StudentExamMarkDTO(1L, "MidTerm", 85.0, 2L, 3L)
        );

        when(marksService.getMarksForStudentExam(eq(10L), eq("MidTerm"))).thenReturn(marks);

        mockMvc.perform(get("/marks/student-exam")
                .param("studentId", "10")
                .param("examName", "MidTerm"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].examName").value("MidTerm"))
                .andExpect(jsonPath("$[0].score").value(85.0))
                .andExpect(jsonPath("$[0].subjectId").value(2L))
                .andExpect(jsonPath("$[0].classId").value(3L));
    }
}
