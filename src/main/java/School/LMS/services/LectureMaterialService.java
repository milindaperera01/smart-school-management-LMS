package School.LMS.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import School.LMS.models.*;
import School.LMS.repos.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;

@Service
@RequiredArgsConstructor
public class LectureMaterialService {
    @Autowired
    private final LectureMaterialRepo lectureRepo;

    @Autowired
    private final TeacherRepo teacherRepo;

    @Autowired
    private final SubjectRepo subjectRepo;

    @Autowired
    private final ClassRoomRepo classRoomRepo;

    private final String UPLOAD_DIR =
        System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "lectures";


    public void uploadLecture(
            String username,
            Long subjectId,
            Long classId,
            String title,
            MultipartFile file
    ) throws Exception {

        Teacher teacher = teacherRepo.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        ClassRoom classroom = classRoomRepo.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        file.transferTo(filePath.toFile());


        LectureMaterial lm = new LectureMaterial();
        lm.setTitle(title);
        lm.setFileName(file.getOriginalFilename());
        lm.setFilePath(filePath.toString()); 
        lm.setUploadedAt(LocalDateTime.now());
        lm.setTeacher(teacher);
        lm.setSubject(subject);
        lm.setClassroom(classroom);

        lectureRepo.save(lm);
    }

    public List<LectureMaterial> getLecturesForClass(Long classId) {
        return lectureRepo.findByClassroomId(classId);
    }

    public LectureMaterial getLectureById(Long id){
        return lectureRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Lecture not found with id: " + id));
    }

    public List<LectureMaterial> getLecturesForClassAndSubject(Long classId, Long subjectId) {
    return lectureRepo.findByClassroomIdAndSubjectId(classId, subjectId);
    }


    
}
