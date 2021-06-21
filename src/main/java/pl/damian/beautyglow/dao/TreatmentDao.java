package pl.damian.beautyglow.dao;

import pl.damian.beautyglow.entity.Treatment;

import java.util.List;

public interface TreatmentDao {


     void deleteTreatment(int id);

     void saveTreatment(Treatment treatment);

     Treatment getTreatment(int id);

     List<Treatment> getTreatments();
}
