package School.LMS.repos;

import School.LMS.models.GradeSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import School.LMS.models.Subject;

public interface GradeSubjectRepo extends JpaRepository<GradeSubject, Long> {
    List<GradeSubject> findByGradeLevel(int gradeLevel);
    boolean existsByGradeLevelAndSubject(int gradeLevel, Subject subject);
}
