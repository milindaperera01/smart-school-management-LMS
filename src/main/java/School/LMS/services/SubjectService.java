package School.LMS.services;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import School.LMS.repos.SubjectRepo;
import lombok.RequiredArgsConstructor;
import School.LMS.models.Subject;
import School.LMS.dto.SubjectDTO;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepo subjectRepository;


    public Subject createSubect(SubjectDTO subjectDTO) {
        if (subjectRepository.existsByName(subjectDTO.getName())) {
            throw new RuntimeException("Subject already exists!");
        }
        Subject subject = new Subject();
        subject.setName(subjectDTO.getName());
        return subjectRepository.save(subject);
    }
    
}
