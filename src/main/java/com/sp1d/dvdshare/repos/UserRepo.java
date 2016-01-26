package com.sp1d.dvdshare.repos;

import com.sp1d.dvdshare.entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sp1d
 */
@Repository
public class UserRepo {

    @PersistenceContext
    EntityManager em;

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u").getResultList();
    }

    public User findById(long id) {
        return em.find(User.class, id);
    }

    public User findByUsername(String username) {
        return (User) em.createQuery("SELECT u FROM User u WHERE u.username = :username")
                .setParameter("username", username).getSingleResult();
    }

    public User findByEmail(String email) {
        User user;
        try {
            user = (User) em.createQuery("SELECT u FROM User u WHERE u.email = :email")
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return user;
    }

    public User add(User t) {
        em.persist(t);
        return em.merge(t);
    }

    public User save(User t) {
        return em.merge(t);
    }

    public void delete(User t) {
        em.remove(t);
    }

}
