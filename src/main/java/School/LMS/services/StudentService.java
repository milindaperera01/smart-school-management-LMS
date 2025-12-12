package School.LMS.services;

import School.LMS.repos.StudentRepo;
import School.LMS.repos.UserRepo;
import School.LMS.models.Student;
import School.LMS.models.Users;
import java.util.Optional;
import School.LMS.models.ClassRoom;
import School.LMS.dto.StudentRegistrationDTO;
import School.LMS.dto.StudentContactUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import School.LMS.repos.ClassRoomRepo;


@Service
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    private final UserRepo userRepository;

    @Autowired
    private final StudentRepo studentRepository;

    @Autowired
    private final ClassRoomRepo classroomRepo;

    public Student registerStudent(StudentRegistrationDTO studentDTO, String username) {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        ClassRoom classRoom = classroomRepo.findById(Long.parseLong(studentDTO.getClassroomId()))
        .orElseThrow(() -> new RuntimeException("Classroom not found"));

            

        if (studentRepository.findByUserUsername(username).isPresent()) {
            throw new RuntimeException("Student profile already exists for this user");
        }

        Student student = new Student();
        student.setId(Long.parseLong(studentDTO.getId()));
        student.setName(studentDTO.getName());
        student.setGender(studentDTO.getGender());
        student.setStatus(studentDTO.getStatus());
        student.setHome_contact1(studentDTO.getHome_contact1());
        student.setHome_contact2(studentDTO.getHome_contact2());
        student.setDateOfBirth(LocalDate.parse(studentDTO.getDateOfBirth()));
        student.setUser(user);
        student.setClassRoom(classRoom);
        

        return studentRepository.save(student);
    }

    public Student updateHomeContact(StudentContactUpdateDTO contactDTO, String username) {
        Student student = studentRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setHome_contact1(contactDTO.getHome_contact1());
        student.setHome_contact2(contactDTO.getHome_contact2());

        return studentRepository.save(student);
    }

    public boolean isProfileCompleted(String username) {
        return studentRepository.findByUserUsername(username).isPresent();
    }


}