package School.LMS.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import School.LMS.models.LectureMaterial;

import java.util.List;

public interface LectureMaterialRepo extends JpaRepository<LectureMaterial, Long> {

    List<LectureMaterial> findByClassroomId(Long classId);
    List<LectureMaterial> findByClassroomIdAndSubjectId(Long classId, Long subjectId);
}
