package pl.damian.beautyglow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.damian.beautyglow.dao.RoleDao;
import pl.damian.beautyglow.dao.UserDao;
import pl.damian.beautyglow.entity.Role;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.user.NewUser;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;



	@Override
	@Transactional
	public User findByEmailAddress(String userName) {
		return userDao.findByEmailAddress(userName);
	}

	@Override
	@Transactional
	public void save(NewUser newUser) {
		User user = new User();
		user.setPassword(passwordEncoder.encode(newUser.getPassword()));
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		user.setEmail(newUser.getEmail());
		user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_CUSTOMER")));
		userDao.save(user);
		emailService.sendSimpleMessage(newUser.getEmail(),"Potwierdź konto","Potwierdź");
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findByEmailAddress(email);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid email or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
