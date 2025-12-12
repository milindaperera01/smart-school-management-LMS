package School.LMS.services;

import School.LMS.repos.PrincipalRepo;
import School.LMS.models.Principal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PrincipalService {
    @Autowired
    private final PrincipalRepo principalRepository;

    public boolean isProfileCompleted(String username) {
        Optional<Principal> principal = principalRepository.findByUserUsername(username);
        return principal.isPresent();  // profile completed if principal record exists
    }
}