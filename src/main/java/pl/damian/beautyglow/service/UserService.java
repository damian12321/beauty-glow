package pl.damian.beautyglow.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.user.NewUser;

import java.util.List;

public interface UserService extends UserDetailsService {

    public User findByEmailAddress(String email);

    public void save(NewUser newUser, boolean skipEmail);

    public boolean resetPassword(String email, String validationKey);

    public boolean validateEmailAddress(String email, String validationKey);

    public boolean remindPassword(String email, boolean skipEmail);

    public boolean changePassword(String email, String password);

    public void updateData(User user);

    public void changeEmail(User user, boolean skipEmail);

    public List<User> getAllUsers();

    public void deleteUser(int id);
}
