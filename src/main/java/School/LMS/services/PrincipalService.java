package School.LMS.services;

import School.LMS.repos.PrincipalRepo;
import School.LMS.models.Principal;
import School.LMS.models.Users;
import School.LMS.repos.UserRepo;
import School.LMS.dto.PrincipalRegistrationDTO;
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

    @Autowired
    private final UserRepo userRepo;

    public boolean isProfileCompleted(String username) {
        Optional<Principal> principal = principalRepository.findByUserUsername(username);
        return principal.isPresent();  // profile completed if principal record exists
    }

        public void registerProfile(PrincipalRegistrationDTO dto, String username) {

        Users user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }


        if (principalRepository.findByUserUsername(username).isPresent()) {
            throw new RuntimeException("Profile already completed");
        }

        Principal principal = new Principal();
        principal.setName(dto.getName());
        principal.setDateOfBirth(dto.getDateOfBirth());
        principal.setEnterDate(dto.getEnterDate());
        principal.setStatus(dto.getStatus());
        principal.setContact(dto.getContact());
        principal.setUser(user);

        principalRepository.save(principal);
    }
}