package pl.damian.beautyglow.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.user.NewUser;

import java.util.List;

public interface UserService extends UserDetailsService {

     User findByEmailAddress(String email);

     void save(NewUser newUser, boolean skipEmail);

     boolean resetPassword(String email, String validationKey);

     boolean validateEmailAddress(String email, String validationKey);

     boolean remindPassword(String email, boolean skipEmail);

     boolean changePassword(String email, String password);

     void updateData(User user);

     void changeEmail(User user, boolean skipEmail);

     List<User> getAllUsers();

     void deleteUser(int id);
}
