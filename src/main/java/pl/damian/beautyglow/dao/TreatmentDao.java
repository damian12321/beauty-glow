package pl.damian.beautyglow.dao;

import pl.damian.beautyglow.entity.Treatment;

import java.util.List;

public interface TreatmentDao {



    public void deleteTreatment(int id);

    public void saveTreatment(Treatment treatment);

    public Treatment getTreatment(int id);

    public List<Treatment> getTreatments();
}
