package School.LMS.repos;

import School.LMS.models.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClassRoomRepo extends JpaRepository<ClassRoom, Long> {
    Optional<ClassRoom> findByGradeLevelAndClassName(int gradeLevel, String className);
    Optional<ClassRoom> findByTeacherId(Long teacherId);
}
