package School.LMS.controller;
import School.LMS.dto.PrincipalRegistrationDTO;
import School.LMS.services.PrincipalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/principal")
@RequiredArgsConstructor
@CrossOrigin
public class PrincipalController {

    @Autowired
    private final PrincipalService principalService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('PRINCIPAL')")
    public ResponseEntity<?> registerPrincipal(
            @Valid @RequestBody PrincipalRegistrationDTO dto,
            Principal principal) {

        principalService.registerProfile(dto, principal.getName());
        return ResponseEntity.ok("Principal profile completed");
    }
}
