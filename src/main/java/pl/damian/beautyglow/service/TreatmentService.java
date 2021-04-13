package pl.damian.beautyglow.service;

import pl.damian.beautyglow.entity.Treatment;

import java.util.List;

public interface TreatmentService {

    public void saveTreatment(Treatment treatment);

    public void deleteTreatment(int id);

    public Treatment getTreatment(int id);

    public List<Treatment> getTreatments();
}
