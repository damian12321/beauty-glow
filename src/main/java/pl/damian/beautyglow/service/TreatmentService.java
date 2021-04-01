package pl.damian.beautyglow.service;

import pl.damian.beautyglow.entity.Treatment;

import java.util.List;

public interface TreatmentService {

    public void addTreatment(Treatment treatment);

    public void deleteTreatment(int id);

    public void updateTreatment(Treatment treatment);

    public Treatment getTreatment(int id);

    public List<Treatment> getTreatments();
}
