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

@Service
@RequiredArgsConstructor
public class TeacherService {

    @Autowired
    private final TeacherRepo teacherRepository;
    
    @Autowired
    private final SubjectRepo subjectRepository;

    @Autowired
    private final UserRepo usersRepo;

    @PreAuthorize("hasRole('TEACHER')")
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
        teacher.setUser(user);

        List<Subject> subjects = subjectRepository.findAllById(dto.getSubjectIds());

        subjects.forEach(sub -> {
            if (sub.getTeachers() == null) {
                sub.setTeachers(new ArrayList<>());
            }
            sub.getTeachers().add(teacher);
        });

        teacherRepository.save(teacher);
    }


}
