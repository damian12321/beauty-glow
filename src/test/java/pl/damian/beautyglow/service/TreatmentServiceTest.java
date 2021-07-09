package pl.damian.beautyglow.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.damian.beautyglow.entity.Treatment;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class TreatmentServiceTest {

    private static Treatment treatment;
    @Autowired
    private TreatmentService treatmentService;
    @BeforeAll
    static void init()
    {
        treatment=new Treatment("Treatment1",90,90,new ArrayList<>());
    }
    @Test
    @Order(4)
    void shouldDeleteTreatment() {
        treatmentService.deleteTreatment(treatment.getId());
        Treatment treatment1=treatmentService.getTreatment(treatment.getId());
        assertNull(treatment1);
    }

    @Test
    @Order(1)
    void shouldSaveTreatment() {
        treatmentService.saveTreatment(treatment);
        assertTrue(treatment.getId()>0);
        treatment.setCost(200);
        treatmentService.saveTreatment(treatment);
        treatment=treatmentService.getTreatment(treatment.getId());
        assertEquals(200,treatment.getCost());
    }

    @Test
    @Order(2)
    void shouldReturnTreatment() {
        Treatment treatment1=treatmentService.getTreatment(treatment.getId());
        assertTrue(treatment1.getId()>0);
    }

    @Test
    @Order(3)
    void shouldReturnTreatments() {
        List<Treatment> treatmentList=treatmentService.getTreatments();
        assertTrue(treatmentList.size()>0);
    }
}
