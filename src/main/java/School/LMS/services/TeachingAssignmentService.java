package School.LMS.services;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import School.LMS.repos.SubjectRepo;
import lombok.RequiredArgsConstructor;
import School.LMS.models.Subject;
import School.LMS.dto.SubjectDTO;
import School.LMS.repos.GradeSubjectRepo;
import School.LMS.repos.TeachingAssignmentRepo;
import School.LMS.models.TeachingAssignment;    
import School.LMS.models.ClassRoom;
import School.LMS.models.GradeSubject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import School.LMS.models.Teacher;
import School.LMS.models.Subject;
import School.LMS.repos.TeacherRepo;
import School.LMS.repos.ClassRoomRepo;




@Service
@RequiredArgsConstructor
public class TeachingAssignmentService {
    @Autowired
    private final TeachingAssignmentRepo repo;

    @Autowired
    private final SubjectRepo subjectRepository;

    @Autowired
    private final TeacherRepo teacherRepository;

    @Autowired
    private final ClassRoomRepo classRoomRepository;


    public TeachingAssignment assignTeacher(
            Long classId,
            Long subjectId,
            Long teacherId
    ) {
        ClassRoom classroom = classRoomRepository.findById(classId).orElse(null);
        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if (repo.existsByClassroomAndSubject(classroom, subject)) {
            throw new RuntimeException("Teacher already assigned for this subject");
        }

        TeachingAssignment ta = new TeachingAssignment();
        ta.setClassroom(classroom);
        ta.setSubject(subject);
        ta.setTeacher(teacher);

        return repo.save(ta);
}
}