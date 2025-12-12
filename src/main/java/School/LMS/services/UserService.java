package School.LMS.services;


import School.LMS.dto.UserRegReq;
import School.LMS.dto.UserRes;
import School.LMS.models.Users;
import School.LMS.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationProvider authManager;

    @Autowired
    private JWTservice jwtservice;



    public UserRes register(UserRegReq req) {
        if (userRepo.findByUsername(req.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        Users user = new Users();
        user.setUsername(req.getUsername());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole(req.getRole());
        userRepo.save(user);

        return new UserRes(user.getId(), user.getUsername(), user.getRole());
    }

    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword())
        );

        if (authentication.isAuthenticated()){
            return jwtservice.generator(user.getUsername());
        } else {
            return "User not verified";
        }
    }

    public String getRole(String username) {
    Users user = userRepo.findByUsername(username);
    return user.getRole(); // or whatever field stores role
}

}
