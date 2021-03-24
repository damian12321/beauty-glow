package pl.damian.beautyglow.dao;


import pl.damian.beautyglow.entity.User;

public interface UserDao {

    public User findByUserName(String userName);
    
    public void save(User user);
    
}
