package pl.damian.beautyglow.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.damian.beautyglow.entity.Treatment;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.entity.UsersTreatments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsersTreatmentsServiceTest {

    @Autowired
    private UsersTreatmentsService usersTreatmentsService;
    @Autowired
    private UserService userService;
    @Autowired
    private TreatmentService treatmentService;
    private static User user;
    private static UsersTreatments usersTreatments;

    @Test
    @Order(1)
    void shouldAddUsersTreatments() {
        user=userService.findByEmailAddress("admin@gmail.com");
        Treatment treatment=treatmentService.getTreatment(1);
        usersTreatments=new UsersTreatments(user,treatment,new Date(),"planned");
        usersTreatmentsService.addUsersTreatments(usersTreatments);
        assertTrue(usersTreatments.getId()>0);
    }

    @Test
    @Order(6)
    void shouldDeleteUsersTreatments() {
        usersTreatmentsService.deleteUsersTreatments(usersTreatments.getId());
        UsersTreatments tempUserTreatments=usersTreatmentsService.getUsersTreatmentsById(usersTreatments.getId());
        assertNull(tempUserTreatments);
    }

    @Test
    @Order(5)
    void shouldUpdateUsersTreatments() {
        usersTreatments.setStatus("cancelled");
        usersTreatmentsService.updateUsersTreatments(usersTreatments);
        UsersTreatments tempUserTreatments=usersTreatmentsService.getUsersTreatmentsById(usersTreatments.getId());
        assertEquals(usersTreatments.getStatus(),tempUserTreatments.getStatus());
    }

    @Test
    @Order(2)
    void shouldReturnUsersTreatments() {
        List<UsersTreatments> usersTreatmentsList=usersTreatmentsService.getUsersTreatments();
        assertTrue(usersTreatmentsList.size()>0);
    }

    @Test
    @Order(4)
    void shouldReturnUsersTreatmentsById() {
        UsersTreatments tempUserTreatments=usersTreatmentsService.getUsersTreatmentsById(usersTreatments.getId());
        assertNotNull(tempUserTreatments);
    }

    @Test
    @Order(3)
    void shouldReturnUsersTreatmentsOnSpecificDay() {
        List<UsersTreatments> usersTreatmentsList=usersTreatmentsService.getUsersTreatmentsOnSpecificDay(new Date());
        assertTrue(usersTreatmentsList.size()>0);
    }
}