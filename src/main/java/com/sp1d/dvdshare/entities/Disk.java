/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sp1d.dvdshare.service.UserSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author sp1d
 */
@Entity
@Table(name = "disks")
@NamedQueries({
    @NamedQuery(name = "OWN", query = "SELECT d FROM Disk d WHERE d.owner = :user"),
    @NamedQuery(name = "HOLD", query = "SELECT d FROM Disk d WHERE d.holder = :user"),
    @NamedQuery(name = "GIVEN", query = "SELECT d FROM Disk d WHERE d.owner = :user AND d.holder != :user"),
    @NamedQuery(name = "TAKEN", query = "SELECT d FROM Disk d WHERE d.owner != :user AND d.holder = :user"),
    @NamedQuery(name = "FOREIGN", query = "SELECT d FROM Disk d WHERE d.owner != :user")
})

public class Disk implements Serializable {
    private static final long serialVersionUID = 123940492941695808L;


    @Transient
    private static final Logger LOG = LogManager.getLogger(Disk.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disk_id", nullable = false)
    private long id;

    @Column(name = "disk_title", nullable = false)
    private String title;

    @JsonSerialize(using = UserSerializer.class)
    @ManyToOne
    @JoinColumn(name = "disk_owner", nullable = false)
    private User owner;

    @JsonSerialize(using = UserSerializer.class)
    @ManyToOne
    @JoinColumn(name = "disk_holder", nullable = false)
    private User holder;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "disk_request")
    private DiskRequest request;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getHolder() {
        return holder;
    }

    public void setHolder(User holder) {
        this.holder = holder;
    }

    public DiskRequest getRequest() {
        return request;
    }

    public void setRequest(DiskRequest request) {
        this.request = request;
    }



    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 17 * hash + Objects.hashCode(this.title);
        hash = 17 * hash + Objects.hashCode(this.owner);
        hash = 17 * hash + Objects.hashCode(this.holder);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Disk other = (Disk) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.owner, other.owner)) {
            return false;
        }
        if (!Objects.equals(this.holder, other.holder)) {
            return false;
        }
        return true;
    }


    public enum Field {

        ID, TITLE, OWNER, HOLDER
    }

}
