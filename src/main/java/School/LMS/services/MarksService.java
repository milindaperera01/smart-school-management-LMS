package School.LMS.services;

import School.LMS.dto.BatchMarksAdd;
import School.LMS.dto.MarksAdd;
import School.LMS.dto.StudentExamMarkDTO;
import School.LMS.models.*;
import School.LMS.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    // Existing method - Unchanged
    public String addMark(MarksAdd req) {
        Student student = studentRepo.findById(req.getStudentId())
                .orElseThrow(() -> new RuntimeException("Invalid student ID: " + req.getStudentId()));

        ClassRoom classroom = classRoomRepo.findById(req.getClassId())
                .orElseThrow(() -> new RuntimeException("Invalid class ID: " + req.getClassId()));

        Subject subject = null;
        if (req.getSubjectId() != null) {
            subject = subjectRepo.findById(req.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Invalid subject ID: " + req.getSubjectId()));
        }

        Marks mark = new Marks();
        mark.setExam_name(req.getExamName());
        mark.setScore(req.getScore());
        mark.setStudent(student);
        mark.setClassroom(classroom);
        mark.setSubject(subject);

        marksRepo.save(mark);
        return "Marks added successfully";
    }

    // New method for bulk saving
    @Transactional
    public String addBatchMarks(BatchMarksAdd req) {
        String rawClass = req.getClassroom();
        String[] parts = rawClass.split("-");
        int gradeLevel = Integer.parseInt(parts[0]);
        String classLetter = parts[1];
        System.out.println("Parsed class: Grade " + gradeLevel + ", Letter " + classLetter);
        ClassRoom classroom = classRoomRepo.findByGradeLevelAndClassName(gradeLevel, classLetter)
                .orElseThrow(() -> new RuntimeException("Invalid class ID: " + req.getClassroom()));

        Subject subject = subjectRepo.findByName(req.getSubject())
                .orElseThrow(() -> new RuntimeException("Invalid subject ID: " + req.getSubject()));

        // 2. Map the entries to Marks entities
        List<Marks> marksList = req.getMarks().entrySet().stream().map(entry -> {
            // Assuming the Key in your JSON map is the Student ID (Long)
            Long studentId = Long.parseLong(entry.getKey()); 
            Double score = entry.getValue();

            Student student = studentRepo.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found ID: " + studentId));

            Marks mark = new Marks();
            mark.setExam_name(req.getExam_name());
            mark.setScore(score);
            mark.setStudent(student);
            mark.setClassroom(classroom);
            mark.setSubject(subject);
            return mark;
        }).collect(Collectors.toList());

        // 3. Batch save
        marksRepo.saveAll(marksList);

        return "Successfully added marks for " + marksList.size() + " students";
    }

    public List<StudentExamMarkDTO> getMarksForStudentExam(Long studentId, String examName) {
        if (!studentRepo.existsById(studentId)) {
            throw new RuntimeException("Invalid student ID: " + studentId);
        }

        return marksRepo.findByStudentIdAndExamName(studentId, examName)
                .stream()
                .map(mark -> new StudentExamMarkDTO(
                        mark.getId(),
                        mark.getExam_name(),
                        mark.getScore(),
                        mark.getSubject() != null ? mark.getSubject().getId() : null,
                        mark.getClassroom() != null ? mark.getClassroom().getId() : null
                ))
                .collect(Collectors.toList());
    }
}
