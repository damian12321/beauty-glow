package pl.damian.beautyglow.dao;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.damian.beautyglow.entity.User;
import pl.damian.beautyglow.service.EmailService;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Random;

@Repository
public class UserDaoImpl implements UserDao {

    private final Session session;

    private final EmailService emailService;

    @Autowired
    public UserDaoImpl(EntityManager entityManager, EmailService emailService) {
        this.session = entityManager.unwrap(Session.class);
        this.emailService = emailService;

    }

    @Override
    public User findByEmailAddress(String email) {

        Query<User> theQuery = session.createQuery("from User where email=:theEmail", User.class);
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
    public void save(User theUser, boolean skipEmail) {
        session.saveOrUpdate(theUser);
    }

    @Override
    public boolean remindPassword(String email, boolean skipEmail) {
        Query<User> theQuery = session.createQuery("from User where email=:theEmail", User.class);
        theQuery.setParameter("theEmail", email);
        User theUser;
        try {
            theUser = theQuery.getSingleResult();
            String key = new Random().nextInt(500000000) + "";
            theUser.setValidationKey(key);
            session.update(theUser);
            if (!skipEmail)
                emailService.sendSimpleMessage(email, "Resetowanie hasła",
                        emailService.textResetMessage(theUser.getFirstName(), theUser.getEmail(), theUser.getValidationKey()));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean validateEmailAddress(String email, String validationKey) {
        Query<User> theQuery = session.createQuery("from User where email=:theEmail", User.class);
        theQuery.setParameter("theEmail", email);
        User theUser;
        try {
            theUser = theQuery.getSingleResult();
            boolean result = validationKey.equals(theUser.getValidationKey());
            if (result && !theUser.getIsActive()) {
                theUser.setIsActive(true);
                session.update(theUser);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean resetPassword(String email, String validationKey) {
        Query<User> theQuery = session.createQuery("from User where email=:theEmail", User.class);
        theQuery.setParameter("theEmail", email);
        User theUser;
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
        Query<User> theQuery = session.createQuery("from User where email=:theEmail", User.class);
        theQuery.setParameter("theEmail", email);
        User theUser;
        try {
            theUser = theQuery.getSingleResult();
            theUser.setPassword(password);
            String key = new Random().nextInt(500000000) + "";
            theUser.setValidationKey(key);
            session.update(theUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateData(User user) {
        session.merge(user);
    }

    @Override
    public void changeEmail(User user, boolean skipEmail) {
        user.setIsActive(false);
        String key = new Random().nextInt(500000000) + "";
        user.setValidationKey(key);
        if (!skipEmail)
            emailService.sendSimpleMessage(user.getEmail(), "Aktywacja konta",
                    emailService.textRegisterMessage(user.getFirstName(), user.getEmail(), user.getValidationKey()));
        session.update(user);
    }

    @Override
    public List<User> getAllUsers() {
        return session.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void deleteUser(int id) {
        User user = session.get(User.class, id);
        session.delete(user);
    }
}
