/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.repos;

import com.sp1d.dvdshare.entities.TakenItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        em.remove(t);
    }

}
