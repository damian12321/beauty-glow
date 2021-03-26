package pl.damian.beautyglow.dao;


import pl.damian.beautyglow.entity.User;

public interface UserDao {

    public User findByEmailAddress(String email);
    
    public void save(User user);
    
}
