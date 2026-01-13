package School.LMS.repos;

import School.LMS.models.StudentAssignment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentAssignmentRepo extends JpaRepository<StudentAssignment, Long> {
    List<StudentAssignment> findByStudentId(Long studentId);
    List<StudentAssignment> findByAssignmentId(Long assignmentId);
    List<StudentAssignment> findByStudentIdAndAssignmentSubjectId(
        Long studentId,
        Long subjectId
);
    Optional<StudentAssignment> findByAssignmentIdAndStudentUserUsername(Long assignmentId, String username);


}