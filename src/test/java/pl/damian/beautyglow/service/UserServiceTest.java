package pl.damian.beautyglow.service;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.user.NewUser;


import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {

    @Autowired
    private UserService userService;

    private static User user;
    private static NewUser newUser;

    @BeforeAll
    public static void init() {
        user = new User("tempEmail", "password", "test", "test");
        newUser = new NewUser("tempEmail", "test", "test", "Test", "Test", new Date());
    }

    @Test
    @Order(2)
    void shouldFindUserByEmailAddress() {
        user = userService.findByEmailAddress(newUser.getEmail());
        assertNotNull(user);
    }

    @Test
    @Order(1)
    void shouldSaveUser() {
        userService.save(newUser, true);
        assertNotNull(newUser);
    }

    @Test
    @Order(8)
    void shouldResetUserPassword() {
        assertTrue(userService.resetPassword(user.getEmail(),user.getValidationKey()));

    }

    @Test
    @Order(4)
    void shouldValidateUserEmailAddress() {
        userService.validateEmailAddress(user.getEmail(), "bad Key");
        user = userService.findByEmailAddress(newUser.getEmail());
        assertFalse(user.getIsActive());
        userService.validateEmailAddress(user.getEmail(), user.getValidationKey());
        user = userService.findByEmailAddress(newUser.getEmail());
        assertTrue(user.getIsActive());

    }

    @Test
    @Order(7)
    void shouldRemindUserPassword() {
        String key=user.getValidationKey();
        userService.remindPassword(user.getEmail(),true);
        user=userService.findByEmailAddress(user.getEmail());
        assertNotEquals(key,user.getValidationKey());
    }

    @Test
    @Order(9)
    void shouldChangeUserPassword() {
        assertTrue(userService.changePassword(user.getEmail(),"123"));
    }

    @Test
    @Order(5)
    void shouldUpdateUserData() {
        user.setLastName("test1");
        userService.updateData(user);
        User tempUser=userService.findByEmailAddress(user.getEmail());
        assertEquals("test1",tempUser.getLastName());
    }

    @Test
    @Order(6)
    void shouldChangeUserEmail() {
        user.setEmail("newEmail");
        userService.changeEmail(user,true);
        User tempUser=userService.findByEmailAddress(user.getEmail());
        assertNotNull(tempUser);
    }

    @Test
    @Order(3)
    void shouldReturnAllUsers() {
        List<User> userList=userService.getAllUsers();
        assertTrue(userList.size()>0);
    }

    @Test
    @Order(10)
    void shouldDeleteUser() {
        userService.deleteUser(user.getId());
        User tempUser=userService.findByEmailAddress(user.getEmail());
        assertNull(tempUser);
    }
}