package School.LMS.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import School.LMS.repos.ClassRoomRepo;
import School.LMS.models.ClassRoom;
import School.LMS.dto.ClassRoomDTO;
import School.LMS.models.Teacher;
import School.LMS.repos.TeacherRepo;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@RequiredArgsConstructor
public class ClassRoomService {

    @Autowired
    private final ClassRoomRepo classRoomRepository;

    @Autowired
    private final TeacherRepo teacherRepository;

    @PreAuthorize("hasRole('PRINCIPAL')")
    public ClassRoom createClassRoom(ClassRoomDTO classRoomDTO) {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setClassName(classRoomDTO.getClassName());
        classRoom.setGradeLevel(classRoomDTO.getGradeLevel());

        if (classRoomDTO.getTeacherusername() != null){
            Teacher teacher = teacherRepository.findByUserUsername(classRoomDTO.getTeacherusername()).orElseThrow(() -> new RuntimeException("Teacher not found"));
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

        if (classRoomDTO.getTeacherusername() != null){
            Teacher teacher = teacherRepository.findByUserUsername(classRoomDTO.getTeacherusername()).orElseThrow(() -> new RuntimeException("Teacher not found"));
        classRoom.setTeacher(teacher);
        }

        return classRoomRepository.save(classRoom);
    }

    
}

