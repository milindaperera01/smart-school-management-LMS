package School.LMS.services;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import School.LMS.repos.SubjectRepo;
import lombok.RequiredArgsConstructor;
import School.LMS.models.Subject;
import School.LMS.dto.SubjectDTO;
import School.LMS.repos.GradeSubjectRepo;
import School.LMS.models.ClassRoom;
import School.LMS.models.GradeSubject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import School.LMS.repos.ClassRoomRepo;

@Service
@RequiredArgsConstructor
public class SubjectService {
    @Autowired
    private final SubjectRepo subjectRepository;

    @Autowired
    private final GradeSubjectRepo gradeSubjectRepository;

    @Autowired
    private final ClassRoomRepo classRoomRepository;

    public Subject createSubect(SubjectDTO subjectDTO) {
        if (subjectRepository.existsByName(subjectDTO.getName())) {
            throw new RuntimeException("Subject already exists!");
        }
        Subject subject = new Subject();
        subject.setName(subjectDTO.getName());
        return subjectRepository.save(subject);
    }

    public List<SubjectDTO> getSubjectsForClass(Long classId) {

    ClassRoom classRoom = classRoomRepository.findById(classId)
            .orElseThrow(() -> new RuntimeException("Classroom not found"));

    int gradeLevel = classRoom.getGradeLevel();

    List<GradeSubject> gradeSubjects =
            gradeSubjectRepository.findByGradeLevel(gradeLevel);

    return gradeSubjects.stream()
            .map(gs -> new SubjectDTO(
                    gs.getSubject().getId(),
                    gs.getSubject().getName()
            ))
            .toList();
}

    
}
