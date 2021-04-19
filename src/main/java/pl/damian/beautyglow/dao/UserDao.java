package pl.damian.beautyglow.dao;


import pl.damian.beautyglow.entity.User;

import java.util.List;

public interface UserDao {

    public User findByEmailAddress(String email);

    public void save(User user, boolean skipEmail);

    public boolean resetPassword(String email, String validationKey);

    public boolean validateEmailAddress(String email, String validationKey);

    public boolean remindPassword(String email, boolean skipEmail);

    public boolean changePassword(String email, String password);

    public void updateData(User user);

    public void changeEmail(User user, boolean skipEmail);

    public List<User> getAllUsers();

    public void deleteUser(int id);
}
