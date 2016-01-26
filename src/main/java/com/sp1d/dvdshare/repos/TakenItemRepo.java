package com.sp1d.dvdshare.repos;

import com.sp1d.dvdshare.entities.TakenItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sp1d
 */
@Repository
public class TakenItemRepo {

    @PersistenceContext
    EntityManager em;

    public TakenItem add(TakenItem t) {
        em.persist(t);
        return em.merge(t);
    }

    public TakenItem save(TakenItem t) {
        return em.merge(t);
    }

    public void delete(TakenItem t) {
        t = em.merge(t);
        em.remove(t);
    }

    public TakenItem findByDiskId(long diskId) {
        Query q = em.createNamedQuery("DISK-ID");
        q.setParameter("id", diskId);
        return (TakenItem) q.getSingleResult();
    }

}
