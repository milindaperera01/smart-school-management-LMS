package School.LMS.repos;

import School.LMS.models.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PrincipalRepo extends JpaRepository<Principal, Long> {
    Optional<Principal> findByUserUsername(String username);
}