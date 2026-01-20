package School.LMS.repos;

import School.LMS.models.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarksRepo extends JpaRepository<Marks, Long> {
    @Query("select m from Marks m where m.student.id = :studentId and m.exam_name = :examName")
    List<Marks> findByStudentIdAndExamName(Long studentId, String examName);
}
