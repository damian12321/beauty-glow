package pl.damian.beautyglow.service;

import pl.damian.beautyglow.entity.Treatment;

import java.util.List;

public interface TreatmentService {

     void saveTreatment(Treatment treatment);

     void deleteTreatment(int id);

     Treatment getTreatment(int id);

     List<Treatment> getTreatments();
}
