package pl.damian.beautyglow.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.damian.beautyglow.entity.Treatment;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TreatmentDaoImpl implements TreatmentDao {
    @Autowired
    private EntityManager entityManager;



    @Override
    public void deleteTreatment(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Treatment treatment=currentSession.get(Treatment.class,id);
        currentSession.delete(treatment);
    }

    @Override
    public void saveTreatment(Treatment treatment) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(treatment);
    }

    @Override
    public Treatment getTreatment(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Treatment treatment=currentSession.get(Treatment.class,id);
        return treatment;
    }

    @Override
    public List<Treatment> getTreatments() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Treatment> query=currentSession.createQuery("FROM Treatment");
        return query.getResultList();
    }
}
