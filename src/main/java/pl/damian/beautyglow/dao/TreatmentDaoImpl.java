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

    private final Session session;
    @Autowired
    public TreatmentDaoImpl(EntityManager entityManager) {
        session = entityManager.unwrap(Session.class);
    }

    @Override
    public void deleteTreatment(int id) {
        Treatment treatment = session.get(Treatment.class, id);
        session.delete(treatment);
    }

    @Override
    public void saveTreatment(Treatment treatment) {
        session.saveOrUpdate(treatment);
    }

    @Override
    public Treatment getTreatment(int id) {
        return session.get(Treatment.class, id);
    }

    @Override
    public List<Treatment> getTreatments() {
        Query<Treatment> query = session.createQuery("FROM Treatment");
        return query.getResultList();
    }
}
