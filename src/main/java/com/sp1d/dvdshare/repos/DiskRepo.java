/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.repos;

import com.sp1d.dvdshare.entities.Disk;
import com.sp1d.dvdshare.entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sp1d
 */
@Repository
public class DiskRepo {

    @PersistenceContext
    EntityManager em;

    public List<Disk> findAllByHolder(User u) {
        Query q = em.createQuery("SELECT d FROM Disk d WHERE d.holder = :user");
        q.setParameter("user", u);
        return q.getResultList();
    }

    public Disk add(Disk t) {
        em.persist(t);
        return em.merge(t);
    }

    public Disk save(Disk t) {
        return em.merge(t);
    }

    public void delete(Disk t) {
        em.remove(t);
    }
}
