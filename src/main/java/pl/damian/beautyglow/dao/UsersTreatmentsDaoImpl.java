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
public class UsersTreatmentsDaoImpl implements UsersTreatmentsDao{
    @Autowired
    private EntityManager entityManager;
    @Override
    public void addUsersTreatments(UsersTreatments usersTreatments) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.save(usersTreatments);
    }

    @Override
    public void deleteUsersTreatments(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        UsersTreatments usersTreatments=currentSession.get(UsersTreatments.class,id);
        currentSession.delete(usersTreatments);
    }

    @Override
    public void updateUsersTreatments(UsersTreatments usersTreatments) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.update(usersTreatments);
    }

    @Override
    public UsersTreatments getUsersTreatments(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        UsersTreatments usersTreatments=currentSession.get(UsersTreatments.class,id);
        return usersTreatments;
    }

    @Override
    public List<UsersTreatments> getUsersTreatments() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<UsersTreatments> query=currentSession.createQuery("FROM UsersTreatments");
        return query.getResultList();
    }

    @Override
    public List<UsersTreatments> getUsersTreatmentsOnSpecificDay(Date date) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<UsersTreatments> query=currentSession.createQuery("FROM UsersTreatments WHERE (date between :date1 AND :date2) " +
                "AND status='planned'");
        Date date1=new Date();
        Date date2=new Date();
       date1.setDate(date.getDate());
        date2.setDate(date.getDate());
        date1.setSeconds(0);
        date1.setMinutes(0);
        date1.setHours(0);
        date2.setSeconds(59);
        date2.setMinutes(59);
        date2.setHours(23);
        query.setParameter("date1",date1);
        query.setParameter("date2",date2);
        return query.getResultList();
    }
}
