/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.repos;

import com.sp1d.dvdshare.entities.Disk;
import com.sp1d.dvdshare.entities.DiskRequest;
import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.service.RequestSelection;
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
public class DiskRequestRepo {

    @PersistenceContext
    EntityManager em;

    public boolean contains(DiskRequest diskRequest) {
        return em.contains(diskRequest);
    }

    public boolean containsDisk(Disk disk) {
        Query q = em.createNamedQuery("COUNT-BYDISK");
        q.setParameter("disk", disk);
        return ((Long)q.getSingleResult()) != 0;
    }

    public DiskRequest findByDiskId(long diskId) {
        Query q = em.createNamedQuery("BYDISK", DiskRequest.class);
        q.setParameter("diskid", diskId);
        return (DiskRequest) q.getSingleResult();
    }

    public DiskRequest findById(long id) {
        return em.find(DiskRequest.class, id);
    }

    public List<DiskRequest> find(RequestSelection selection) {
        Query q = em.createNamedQuery(selection.toString(), DiskRequest.class);
        return q.getResultList();
    }

    public List<DiskRequest> find(RequestSelection selection, User user) {
        Query q = em.createNamedQuery(selection.toString(), DiskRequest.class);
        q.setParameter("user", user);
        return q.getResultList();
    }

    public long count(RequestSelection selection, User user) {
        Query q = em.createNamedQuery("COUNT-"+selection.toString(), Long.class);
        q.setParameter("user", user);
        return (long)q.getSingleResult();
    }

    public long countNewIncoming(RequestSelection selection, User user) {
        Query q = em.createNamedQuery("COUNT-NEW-IN", Long.class);
        q.setParameter("user", user);
        return (long)q.getSingleResult();
    }

    public DiskRequest add(DiskRequest t) {
        em.persist(t);
        return em.merge(t);
    }

    public DiskRequest save(DiskRequest t) {
        return em.merge(t);
    }

    public void delete(DiskRequest t) {
        em.remove(t);
    }
}
