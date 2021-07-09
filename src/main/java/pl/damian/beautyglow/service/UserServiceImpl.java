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

import java.util.*;
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
    public void save(NewUser newUser, boolean skipEmail) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        if (!skipEmail)
            user.setRoles(Collections.singletonList(roleDao.findRoleByName("ROLE_CUSTOMER")));
        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setDate(newUser.getDate());
        String key = new Random().nextInt(500000000) + "";
        user.setValidationKey(key);
        userDao.save(user, false);
        if (!skipEmail)
            emailService.sendSimpleMessage(newUser.getEmail(), "Aktywacja konta",
                    emailService.textRegisterMessage(user.getFirstName(), user.getEmail(), user.getValidationKey()));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmailAddress(email);
        if (user == null || !user.getIsActive()) {
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean resetPassword(String email, String validationKey) {
        return userDao.resetPassword(email, validationKey);
    }

    @Override
    @Transactional
    public boolean validateEmailAddress(String email, String validationKey) {
        return userDao.validateEmailAddress(email, validationKey);
    }

    @Override
    @Transactional
    public boolean remindPassword(String email, boolean skipEmail) {
        return userDao.remindPassword(email, skipEmail);
    }

    @Override
    @Transactional
    public boolean changePassword(String email, String password) {
        password = passwordEncoder.encode(password);
        return userDao.changePassword(email, password);
    }

    @Override
    @Transactional
    public void updateData(User user) {
        userDao.updateData(user);
    }

    @Override
    @Transactional
    public void changeEmail(User user, boolean skipEmail) {
        userDao.changeEmail(user, skipEmail);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }
}
