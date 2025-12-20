package School.LMS.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import School.LMS.repos.ClassRoomRepo;
import School.LMS.models.ClassRoom;
import School.LMS.models.Student;
import School.LMS.dto.ClassRoomDTO;
import School.LMS.models.Teacher;
import School.LMS.models.Users;
import java.security.Principal;
import java.util.List;
import School.LMS.dto.ClassStudentsDTO;
import School.LMS.repos.UserRepo;
import School.LMS.repos.TeacherRepo;
import School.LMS.dto.SubjectDTO;
import School.LMS.dto.StudentDTO;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@RequiredArgsConstructor
public class ClassRoomService {

    @Autowired
    private final ClassRoomRepo classRoomRepository;

    @Autowired
    private final TeacherRepo teacherRepository;

    @Autowired
    private final UserRepo usersRepository;

    @PreAuthorize("hasRole('PRINCIPAL')")
    public ClassRoom createClassRoom(ClassRoomDTO classRoomDTO) {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setClassName(classRoomDTO.getClassName());
        classRoom.setGradeLevel(classRoomDTO.getGradeLevel());

        if (classRoomDTO.getTeacherUsername() != null){
            Teacher teacher = teacherRepository.findByUserUsername(classRoomDTO.getTeacherUsername()).orElseThrow(() -> new RuntimeException("Teacher not found"));
        classRoom.setTeacher(teacher);
        }

        classRoom.setStudents(new ArrayList<>());

        return classRoomRepository.save(classRoom);
    }

    @PreAuthorize("hasRole('PRINCIPAL')")
    public ClassRoom updateClassRoom(Long id, ClassRoomDTO classRoomDTO) {
        ClassRoom classRoom = classRoomRepository.findById(id).orElseThrow(() -> new RuntimeException("ClassRoom not found"));

        if (classRoomDTO.getGradeLevel() != null) {
            classRoom.setGradeLevel(classRoomDTO.getGradeLevel());
        }

        if (classRoomDTO.getTeacherUsername() != null){
            Teacher teacher = teacherRepository.findByUserUsername(classRoomDTO.getTeacherUsername()).orElseThrow(() -> new RuntimeException("Teacher not found"));
        classRoom.setTeacher(teacher);
        }

        return classRoomRepository.save(classRoom);
    }

        public ClassStudentsDTO getStudentsForLoggedInTeacher(Principal principal) {

        String username = principal.getName();

        Users user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        ClassRoom classRoom = classRoomRepository.findByTeacherId(teacher.getId())
                .orElseThrow(() -> new RuntimeException("Teacher has no class assigned"));

        List<StudentDTO> students = classRoom.getStudents()
        .stream()
        .map(s -> new StudentDTO(s.getId(), s.getName()))
        .toList();

        return new ClassStudentsDTO(classRoom.getClassName(), classRoom.getGradeLevel(), students);

    }
    
}

