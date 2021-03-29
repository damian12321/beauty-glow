package pl.damian.beautyglow.dao;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.service.EmailService;

import javax.persistence.EntityManager;
import java.util.Random;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EmailService emailService;

    @Override
    public User findByEmailAddress(String email) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query<User> theQuery = currentSession.createQuery("from User where email=:theEmail", User.class);
        theQuery.setParameter("theEmail", email);
        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return theUser;
    }

    @Override
    public void save(User theUser) {

        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(theUser);
    }

    @Override
    public boolean remindPassword(String email) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<User> theQuery = currentSession.createQuery("from User where email=:theEmail", User.class);
        theQuery.setParameter("theEmail", email);
        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
            String key = new Random().nextInt(500000000) + "";
            theUser.setValidationKey(key);
            currentSession.update(theUser);
            emailService.sendSimpleMessage(email,"Resetowanie hasła",
                    emailService.textResetMessage(theUser.getFirstName(),theUser.getEmail(),theUser.getValidationKey()));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean validateEmailAddress(String email, String validationKey) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<User> theQuery = currentSession.createQuery("from User where email=:theEmail", User.class);
        theQuery.setParameter("theEmail", email);
        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
            boolean result = validationKey.equals(theUser.getValidationKey());
            if (result&&!theUser.getIsActive()) {
                theUser.setIsActive(true);
                currentSession.update(theUser);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean resetPassword(String email, String validationKey) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<User> theQuery = currentSession.createQuery("from User where email=:theEmail", User.class);
        theQuery.setParameter("theEmail", email);
        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
            boolean result = validationKey.equals(theUser.getValidationKey());
            if (result) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean changePassword(String email, String password) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<User> theQuery = currentSession.createQuery("from User where email=:theEmail", User.class);
        theQuery.setParameter("theEmail", email);
        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
            theUser.setPassword(password);
            String key = new Random().nextInt(500000000) + "";
            theUser.setValidationKey(key);
            currentSession.update(theUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
