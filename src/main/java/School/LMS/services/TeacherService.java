package School.LMS.services;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import School.LMS.repos.TeacherRepo;
import lombok.RequiredArgsConstructor;
import School.LMS.dto.TeacherRegistrationDTO;
import School.LMS.models.Teacher;
import School.LMS.models.TeachingAssignment;
import School.LMS.models.Users;
import School.LMS.models.Subject;
import School.LMS.repos.SubjectRepo;
import School.LMS.repos.UserRepo;
import School.LMS.repos.ClassRoomRepo;
import School.LMS.models.ClassRoom;
import java.util.Optional;
import School.LMS.dto.TeacherClassStatusDTO;
import School.LMS.repos.TeachingAssignmentRepo;
import School.LMS.dto.TeacherAssignmentDTO;
import School.LMS.dto.TeacherDTO;

@Service
@RequiredArgsConstructor
public class TeacherService {

    @Autowired
    private final TeacherRepo teacherRepository;
    
    @Autowired
    private final SubjectRepo subjectRepository;

    @Autowired
    private final UserRepo usersRepo;

    @Autowired
    private final ClassRoomRepo classRoomRepository;

    @Autowired
    private final TeachingAssignmentRepo teachingAssignmentRepo;

    public void registerProfile(TeacherRegistrationDTO dto, String username) {

        Users user = usersRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
}
         
        if (!user.getRole().equals("TEACHER")) {
            throw new RuntimeException("Only teachers can complete this profile");
        }

        Teacher teacher = new Teacher();
        teacher.setName(dto.getName());
        teacher.setDateOfBirth(dto.getDateOfBirth());
        teacher.setGender(dto.getGender());
        teacher.setStatus(dto.getStatus());
        teacher.setContact(dto.getContact());
        teacher.setUser(user);

        List<Subject> subjects = new ArrayList<>();

        for (String subjectName : dto.getSubjectNames()) {
            Subject subject = subjectRepository.findByName(subjectName)
                .orElseThrow(() ->
                    new RuntimeException("Subject not found: " + subjectName));

            subjects.add(subject);
        }

        subjects.forEach(sub -> {
            if (sub.getTeachers() == null) {
                sub.setTeachers(new ArrayList<>());
            }
            sub.getTeachers().add(teacher);
        });

        teacherRepository.save(teacher);
    }

    public boolean isProfileCompleted(String username) {
    Optional<Teacher> teacher = teacherRepository.findByUserUsername(username);
    return teacher.isPresent();  // profile completed if student record exists
    }

    public TeacherClassStatusDTO getTeacherClassStatus(String username) {
        Teacher teacher = teacherRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Optional<ClassRoom> classRoom = classRoomRepository.findByTeacherId(teacher.getId());
        if (classRoom.isEmpty()) {
            return new TeacherClassStatusDTO(false,null);
        }

        return new TeacherClassStatusDTO(true, classRoom.get().getId());
    }

    public List<TeacherAssignmentDTO> getTeacherAssignments(String username) {

    Teacher teacher = teacherRepository.findByUserUsername(username)
            .orElseThrow(() -> new RuntimeException("Teacher not found"));

    return teachingAssignmentRepo.findByTeacher(teacher).stream()
                .map(a -> new TeacherAssignmentDTO(
                        a.getId(),
                        a.getClassroom().getId(),
                        a.getClassroom().getGradeLevel(),
                        a.getClassroom().getClassName(),
                        a.getSubject().getId(),
                        a.getSubject().getName()
                ))
                .toList();
}



    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(t -> new TeacherDTO(
                        t.getId(),
                        t.getName()
                ))
                .toList();
    }


    


}
