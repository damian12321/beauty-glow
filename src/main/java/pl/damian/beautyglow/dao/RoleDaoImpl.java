package pl.damian.beautyglow.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.damian.beautyglow.entity.Role;

import javax.persistence.EntityManager;

@Repository
public class RoleDaoImpl implements RoleDao {

    private final Session session;
    @Autowired
    public RoleDaoImpl(EntityManager entityManager) {
        session = entityManager.unwrap(Session.class);
    }

    @Override
    public Role findRoleByName(String theRoleName) {
        Query<Role> theQuery = session.createQuery("from Role where name=:roleName", Role.class);
        theQuery.setParameter("roleName", theRoleName);

        Role theRole = null;

        try {
            theRole = theQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return theRole;
    }
}
