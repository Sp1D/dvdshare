package com.sp1d.dvdshare.repos;

import com.sp1d.dvdshare.entities.Disk;
import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.service.DiskSelection;
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

    public List<Disk> findUniversalByUser(DiskSelection dataSelection, User user) {
        Query q = em.createNamedQuery(dataSelection.toString());
        q.setParameter("user", user);
        return q.getResultList();
    }

    public Disk findById(long id) {
        return em.find(Disk.class, id);
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
