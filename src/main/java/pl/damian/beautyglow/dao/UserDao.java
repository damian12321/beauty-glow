package pl.damian.beautyglow.dao;


import pl.damian.beautyglow.entity.User;

import java.util.List;

public interface UserDao {

    public User findByEmailAddress(String email);

    public void save(User user);

    public boolean resetPassword(String email, String validationKey);

    public boolean validateEmailAddress(String email, String validationKey);

    public boolean remindPassword(String email);

    public boolean changePassword(String email, String password);

    public void updateData(User user);

    public void changeEmail(User user);

    public List<User> getAllUsers();
}
