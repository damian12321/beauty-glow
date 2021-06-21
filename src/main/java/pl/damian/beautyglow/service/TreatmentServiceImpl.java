package pl.damian.beautyglow.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.damian.beautyglow.dao.TreatmentDao;
import pl.damian.beautyglow.entity.Treatment;

import java.util.List;

@Service
@Transactional
public class TreatmentServiceImpl implements TreatmentService {
    @Autowired
    private TreatmentDao treatmentDao;

    @Override
    public void deleteTreatment(int id) {
        treatmentDao.deleteTreatment(id);
    }

    @Override
    public void saveTreatment(Treatment treatment) {
        treatmentDao.saveTreatment(treatment);
    }

    @Override
    public Treatment getTreatment(int id) {
        return treatmentDao.getTreatment(id);
    }

    @Override
    public List<Treatment> getTreatments() {
        return treatmentDao.getTreatments();
    }
}
