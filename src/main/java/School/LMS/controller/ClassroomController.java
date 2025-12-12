package School.LMS.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import School.LMS.repos.ClassRoomRepo;
import School.LMS.models.ClassRoom;
import School.LMS.dto.ClassRoomDTO;
import School.LMS.models.Teacher;
import School.LMS.repos.TeacherRepo;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import School.LMS.services.ClassRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/classrooms")
@RequiredArgsConstructor
@CrossOrigin
public class ClassroomController {

    @Autowired
    private final ClassRoomService classRoomService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('PRINCIPAL')")
    public ResponseEntity<ClassRoom> createClassRoom(@RequestBody ClassRoomDTO classRoomDTO) {
        ClassRoom createdClassRoom = classRoomService.createClassRoom(classRoomDTO);
        return ResponseEntity.ok(createdClassRoom);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('PRINCIPAL')")
    public ResponseEntity<ClassRoom> updateClassRoom(@PathVariable Long id, @RequestBody ClassRoomDTO classRoomDTO) {
        ClassRoom updatedClassRoom = classRoomService.updateClassRoom(id, classRoomDTO);
        return ResponseEntity.ok(updatedClassRoom);
    }
}
