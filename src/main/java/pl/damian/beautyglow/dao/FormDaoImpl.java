package pl.damian.beautyglow.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.damian.beautyglow.entity.Form;

import javax.persistence.EntityManager;
@Repository
public class FormDaoImpl implements FormDao{
    @Autowired
    private EntityManager entityManager;
    @Override
    public Form findFormByUserId(int userId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Form> theQuery = currentSession.createQuery("from Form where id=:userId", Form.class);
        theQuery.setParameter("userId", userId);

        Form theForm = null;

        try {
            theForm = theQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return theForm;
    }

}
