package pl.damian.beautyglow.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.user.NewUser;

public interface UserService extends UserDetailsService {

    public User findByEmailAddress(String email);

    public void save(NewUser newUser);

    public boolean resetPassword(String email, String validationKey);

    public boolean validateEmailAddress(String email, String validationKey);

    public boolean remindPassword(String email);

    public boolean changePassword(String email, String password);
    }
