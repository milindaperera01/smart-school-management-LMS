package School.LMS.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import School.LMS.models.Assignment;
import java.util.List;

public interface AssignmentRepo extends JpaRepository<Assignment, Long> {
    List<Assignment> findByClassRoomIdAndSubjectId(Long classId, Long subjectId);
}