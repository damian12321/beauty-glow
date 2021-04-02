package pl.damian.beautyglow.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.damian.beautyglow.entity.UsersTreatments;

import javax.persistence.EntityManager;
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
}
