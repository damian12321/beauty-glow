package pl.damian.beautyglow.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.user.CrmUser;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);

	public void save(CrmUser crmUser);
}
