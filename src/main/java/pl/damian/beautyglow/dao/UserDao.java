package pl.damian.beautyglow.dao;


import pl.damian.beautyglow.entity.User;

public interface UserDao {

    public User findByEmailAddress(String email);

    public void save(User user);

    public boolean resetPassword(String email, String validationKey);

    public boolean validateEmailAddress(String email, String validationKey);

    public boolean remindPassword(String email);

    public boolean changePassword(String email, String password);
}
