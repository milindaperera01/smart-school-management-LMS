package School.LMS.services;

import School.LMS.dto.MarksAdd;
import School.LMS.models.*;
import School.LMS.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarksService {

    @Autowired
    private MarksRepo marksRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private ClassRoomRepo classRoomRepo;


    public String addMark(MarksAdd req) {

        // Fetch required objects -------------------------------
        Student student = studentRepo.findById(req.getStudentId())
                .orElseThrow(() -> new RuntimeException("Invalid student ID: " + req.getStudentId()));

        ClassRoom classroom = classRoomRepo.findById(req.getClassId())
                .orElseThrow(() -> new RuntimeException("Invalid class ID: " + req.getClassId()));

        Subject subject = null;
        if (req.getSubjectId() != null) {
            subject = subjectRepo.findById(req.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Invalid subject ID: " + req.getSubjectId()));
        }

        // Create the Marks entity -------------------------------
        Marks mark = new Marks();
        mark.setExam_name(req.getExamName());
        mark.setScore(req.getScore());
        mark.setStudent(student);
        mark.setClassroom(classroom);
        mark.setSubject(subject);  // can be null

        // Save --------------------------------------------------
        marksRepo.save(mark);

        return "Marks added successfully";
    }
}
