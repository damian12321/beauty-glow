package pl.damian.beautyglow.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.damian.beautyglow.entity.UsersTreatments;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
public class UsersTreatmentsDaoImpl implements UsersTreatmentsDao {

    private final Session session;
    @Autowired
    public UsersTreatmentsDaoImpl(EntityManager entityManager) {
        session = entityManager.unwrap(Session.class);
    }

    @Override
    public void addUsersTreatments(UsersTreatments usersTreatments) {
        session.save(usersTreatments);
    }

    @Override
    public void deleteUsersTreatments(int id) {
        UsersTreatments usersTreatments = session.get(UsersTreatments.class, id);
        session.delete(usersTreatments);
    }

    @Override
    public void updateUsersTreatments(UsersTreatments usersTreatments) {
        session.update(usersTreatments);
    }

    @Override
    public UsersTreatments getUsersTreatmentsById(int id) {
        UsersTreatments usersTreatments = session.get(UsersTreatments.class, id);
        return usersTreatments;
    }

    @Override
    public List<UsersTreatments> getUsersTreatments() {
        Query<UsersTreatments> query = session.createQuery("FROM UsersTreatments");
        return query.getResultList();
    }

    @Override
    public List<UsersTreatments> getUsersTreatmentsOnSpecificDay(Date date) {
        Query<UsersTreatments> query = session.createQuery("FROM UsersTreatments WHERE (date between :date1 AND :date2) " +
                "AND status='planned'");
        Date date1 = new Date();
        Date date2 = new Date();
        date1.setDate(date.getDate());
        date2.setDate(date.getDate());
        date1.setSeconds(0);
        date1.setMinutes(0);
        date1.setHours(0);
        date2.setSeconds(59);
        date2.setMinutes(59);
        date2.setHours(23);
        query.setParameter("date1", date1);
        query.setParameter("date2", date2);
        return query.getResultList();
    }
}
